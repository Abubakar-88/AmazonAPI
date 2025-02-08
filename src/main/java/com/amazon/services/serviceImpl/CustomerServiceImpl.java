package com.amazon.services.serviceImpl;

import com.amazon.dto.country.CountryRequestDTO;
import com.amazon.dto.customer.CustomerRequestBody;
import com.amazon.dto.customer.CustomerResponseBody;
import com.amazon.entity.AuthenticationType;
import com.amazon.entity.Country;
import com.amazon.entity.Customer;
import com.amazon.entity.State;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.CountryRepository;
import com.amazon.repository.CustomerRepository;
import com.amazon.services.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    CountryRepository countryRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CustomerResponseBody> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseBody.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponseBody> customerListByPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("firstName").ascending());
        List<Customer> customers = customerRepo.findAll(pageable).getContent();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseBody.class))
                .collect(Collectors.toList());
    }


    @Override
    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }

    @Override
    public CustomerResponseBody registerCustomer(CustomerRequestBody customerRequestBody) {


        if (!isEmailUnique(customerRequestBody.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Customer customer = modelMapper.map(customerRequestBody, Customer.class);

        customer.setEnabled(true);
        customer.setCreatedTime(new Date(System.currentTimeMillis()));
        customer.setAuthenticationType(AuthenticationType.DATABASE);


        if (customerRequestBody.getCountry() == null || customerRequestBody.getCountry().getCode() == null) {
            throw new RuntimeException("Country code can not be null");
        }
        CountryRequestDTO countryDTO = customerRequestBody.getCountry();
        Optional<Country> selectCountry = countryRepo.findByCode(countryDTO.getCode());

        if (!selectCountry.isPresent()) {
            throw new RuntimeException("Country not found");
        }
        Country selectedCuntry = selectCountry.get();
        customer.setCountry(selectedCuntry);


        if (countryDTO.getStates() != null && !countryDTO.getStates().isEmpty()) {
            Optional<State> selectedState = selectedCuntry.getStates().stream().filter(state -> countryDTO.getStates().stream()
                    .anyMatch(reqState -> reqState.getName().equalsIgnoreCase(state.getName()))).findFirst();

            if (selectedState.isPresent()) {
                customer.setState(selectedState.get().getName());
            } else {
                throw new RuntimeException("State not found in the specified country");
            }
        }

        customerRepo.save(customer);

        CustomerResponseBody customerResponseBody = modelMapper.map(customer, CustomerResponseBody.class);
        customerResponseBody.setFullName(customer.getFirstName() + " " + customer.getLastName());
        customerResponseBody.setCountryId(selectedCuntry.getId());
        return customerResponseBody;
    }

    @Override
    public CustomerResponseBody updateCustomer(CustomerRequestBody customerRequestBody, Integer id) throws ResourceNotFoundException {
        // Fetch the existing customer
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        // Use ModelMapper for partial updates
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true); // Skip null values during mapping
        modelMapper.map(customerRequestBody, customer);

        // Handle password separately to avoid overwriting with null
        if (customerRequestBody.getPassword() != null && !customerRequestBody.getPassword().isEmpty()) {
            customer.setPassword(customerRequestBody.getPassword()); // Set raw password directly
        }

        // Validate and update country
        if (customerRequestBody.getCountry() == null || customerRequestBody.getCountry().getCode() == null) {
            throw new RuntimeException("Country code cannot be null");
        }

        CountryRequestDTO countryDTO = customerRequestBody.getCountry();
        Optional<Country> selectedCountryOpt = countryRepo.findByCode(countryDTO.getCode());
        if (!selectedCountryOpt.isPresent()) {
            throw new RuntimeException("Country not found with code: " + countryDTO.getCode());
        }

        Country selectedCountry = selectedCountryOpt.get();
        customer.setCountry(selectedCountry);

        // Validate and update state (if provided)
        if (countryDTO.getStates() != null && !countryDTO.getStates().isEmpty()) {
            Optional<State> selectedStateOpt = selectedCountry.getStates().stream()
                    .filter(state -> countryDTO.getStates().stream()
                            .anyMatch(reqState -> reqState.getName().equalsIgnoreCase(state.getName())))
                    .findFirst();

            if (selectedStateOpt.isPresent()) {
                customer.setState(selectedStateOpt.get().getName());
            } else {
                throw new RuntimeException("State not found in the specified country");
            }
        } else if (customerRequestBody.getState() != null) {
            // Directly set the state name if it is provided as a plain string
            Optional<State> selectedStateOpt = selectedCountry.getStates().stream()
                    .filter(state -> state.getName().equalsIgnoreCase(customerRequestBody.getState()))
                    .findFirst();

            if (selectedStateOpt.isPresent()) {
                customer.setState(selectedStateOpt.get().getName());
            } else {
                throw new RuntimeException("State not found in the specified country");
            }
        }

        // Save the updated customer
        customerRepo.save(customer);

        // Map the updated customer to CustomerResponseBody
        CustomerResponseBody customerResponseBody = modelMapper.map(customer, CustomerResponseBody.class);

        // Set additional fields in the response DTO
        customerResponseBody.setFullName(customer.getFirstName() + " " + customer.getLastName());
        customerResponseBody.setCountryId(selectedCountry.getId());

        return customerResponseBody;
    }


    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }


    @Override
    public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepo.updateEnabledStatus(id, enabled);
    }

    @Override
    public Customer getCustomerById(Integer id) throws ResourceNotFoundException {
        try {
            return customerRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ResourceNotFoundException("Could not find any customers with ID " + id);
        }
    }

    @Override
    public List<Country> listAllCountries() {
        return countryRepo.findAllByOrderByNameAsc();
    }

    @Override
    public void updateAuthenticationType(Customer customer, AuthenticationType type) {
        if (!customer.getAuthenticationType().equals(type)) {
            customer.setAuthenticationType(type);
            customerRepo.save(customer);
        }
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        Long count = customerRepo.countById(id);
        if (count == null || count == 0) {
            throw new ResourceNotFoundException("Could not find any customers with ID " + id);
        }

        customerRepo.deleteById(id);
    }
}
