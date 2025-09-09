package com.maverick.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maverick.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    @Query("select c from Customer c where c.user.username=?1")
    Customer getCustomerByUsername(String username);
}