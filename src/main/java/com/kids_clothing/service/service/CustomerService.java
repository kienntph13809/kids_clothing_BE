package com.kids_clothing.service.service;

import com.kids_clothing.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer findByAccount(Long idAccount);

    Customer findById(Long id);

    List<Customer> findAllFalse();

    Customer update(Customer ct);

}
