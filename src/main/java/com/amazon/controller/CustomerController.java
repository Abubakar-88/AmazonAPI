package com.amazon.controller;

import com.amazon.dto.customer.CustomerRequestBody;
import com.amazon.dto.customer.CustomerResponseBody;
import com.amazon.entity.AuthenticationType;
import com.amazon.entity.Country;
import com.amazon.entity.Customer;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.services.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping()
    public List<CustomerResponseBody> listCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customers by page
    @GetMapping("/page/{pageNum}")
    public ResponseEntity<List<CustomerResponseBody>> listCustomerByPage(
            @PathVariable(name = "pageNum") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<CustomerResponseBody> customerList = customerService.customerListByPage(pageNum, pageSize);
        return ResponseEntity.ok(customerList);
    }



    // Register a new customer
    @PostMapping("/register")
    public ResponseEntity<CustomerResponseBody> saveCustomer(@RequestBody CustomerRequestBody customerRequestBody) {
        CustomerResponseBody response = customerService.registerCustomer(customerRequestBody);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    // Update customer details
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(
            @RequestBody CustomerRequestBody customerRequestBody,
            @PathVariable Integer id) throws ResourceNotFoundException {
        customerService.updateCustomer(customerRequestBody, id);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    // Update customer enabled status
    @PutMapping("/{id}/enabled/{enabled}")
    public ResponseEntity<String> updateCustomerEnabledStatus(
            @PathVariable("id") Integer id,
            @PathVariable("enabled") boolean enabled) {
        customerService.updateCustomerEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        return new ResponseEntity<>("Customer with ID " + id + " has been " + status, HttpStatus.OK);
    }


    // Update customer's authentication type
    @PutMapping("/{customerId}/authentication")
    public ResponseEntity<String> updateAuthenticationType(
            @PathVariable("customerId") Integer customerId,
            @RequestParam("type") AuthenticationType type) throws ResourceNotFoundException {

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity<>("Customer with ID " + customerId + " not found", HttpStatus.NOT_FOUND);
        }

        customerService.updateAuthenticationType(customer, type);
        return new ResponseEntity<>("Authentication type updated successfully for customer with ID " + customerId, HttpStatus.OK);
    }

    // Delete customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {
        try {
            customerService.delete(id);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/countries")
    public List<Country> listAllCountries() {
        return customerService.listAllCountries();
    }

}
