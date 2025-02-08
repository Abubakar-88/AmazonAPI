package com.amazon.services.service;

import com.amazon.dto.customer.CustomerRequestBody;
import com.amazon.dto.customer.CustomerResponseBody;
import com.amazon.entity.AuthenticationType;
import com.amazon.entity.Country;
import com.amazon.entity.Customer;
import com.amazon.exception.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseBody> getAllCustomers();

    List<CustomerResponseBody> customerListByPage(int pageNum, int pageSize);

    boolean isEmailUnique(String email);

    CustomerResponseBody updateCustomer(CustomerRequestBody customerRequestBody, Integer id) throws ResourceNotFoundException;

    CustomerResponseBody registerCustomer(CustomerRequestBody customerRequestBody);

    Customer getCustomerByEmail(String email);

    void updateCustomerEnabledStatus(Integer id, boolean enabled);

    Customer getCustomerById(Integer id) throws ResourceNotFoundException;

    List<Country> listAllCountries();

    void updateAuthenticationType(Customer customer, AuthenticationType type);

    void delete(Integer id) throws ResourceNotFoundException;
}
