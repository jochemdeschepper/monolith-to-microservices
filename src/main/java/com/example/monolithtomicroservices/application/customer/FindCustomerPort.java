package com.example.monolithtomicroservices.application.customer;

import com.example.monolithtomicroservices.domain.Customer;

import java.util.Optional;

public interface FindCustomerPort {
    Optional<Customer> byEmail(String email);
}
