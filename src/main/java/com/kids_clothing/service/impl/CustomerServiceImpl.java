package com.kids_clothing.service.impl;

import com.kids_clothing.entity.Customer;
import com.kids_clothing.repository.CustomerDao;
import com.kids_clothing.service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Override
    public Customer findByAccount(Long idAccount) {
        return customerDao.findByAccount(idAccount);
    }

    @Override
    public List<Customer> findAllFalse() {
        return customerDao.findAllFalse();
    }

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id).get();
    }

    @Override
    public Customer update(Customer ct) {
        return customerDao.save(ct);
    }


}
