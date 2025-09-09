package com.maverick.service;


import java.time.LocalDate;
import java.util.List;

import com.maverick.dto.CustomerRegisterDto;
import com.maverick.entity.Customer;
import com.maverick.entity.User;
import com.maverick.repo.CustomerRepository;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private UserService userService;

    public CustomerService(CustomerRepository customerRepository, UserService userService) {
        super();
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    // insert values into customer
    public Customer postCustomer(CustomerRegisterDto customerRegisterDto) {
        User user = new User();
        user.setUsername(customerRegisterDto.getUsername());
        user.setPassword(customerRegisterDto.getPassword());
        user.setRole("CUSTOMER");
        user = userService.signUp(user);
        Customer customer = new Customer();
        customer.setFirstName(customerRegisterDto.getFirstName());
        customer.setLastName(customerRegisterDto.getLastName());
        customer.setEmail(customerRegisterDto.getEmail());
        customer.setPhoneNumber(customerRegisterDto.getPhoneNumber());
        customer.setAddress(customerRegisterDto.getAddress());
        customer.setDateOfBirth(customerRegisterDto.getDateOfBirth());
        customer.setGender(customerRegisterDto.getGender());
        customer.setRegistrationDate(LocalDate.now());
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    // fetch customer by id
    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
    }

    // fetch all customer
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    // update customer
    public Customer putCustomer(String username, Customer updatedCustomer) {
        Customer dbCustomer = customerRepository.getCustomerByUsername(username);
        if(updatedCustomer.getFirstName() != null)
            dbCustomer.setFirstName(updatedCustomer.getFirstName());
        if(updatedCustomer.getLastName() != null)
            dbCustomer.setLastName(updatedCustomer.getLastName());
        if(updatedCustomer.getEmail() != null)
            dbCustomer.setEmail(updatedCustomer.getEmail());
        if(updatedCustomer.getPhoneNumber() != null)
            dbCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        if(updatedCustomer.getAddress() != null)
            dbCustomer.setAddress(updatedCustomer.getAddress());
        if(updatedCustomer.getDateOfBirth() != null)
            dbCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
        if(updatedCustomer.getGender() != null)
            dbCustomer.setGender(updatedCustomer.getGender());
        return customerRepository.save(dbCustomer);
    }

    // fetch customer by log Cred
    public Customer getCustomerByUsername(String username) {
        return customerRepository.getCustomerByUsername(username);
    }

}
