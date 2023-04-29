package com.kids_clothing.service.impl;

import com.kids_clothing.repository.AccountDao;
import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Customer;
import com.kids_clothing.model.request.AccountDto;
import com.kids_clothing.model.request.CustomerDto;
import com.kids_clothing.model.response.CustomerResponse;
import com.kids_clothing.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Override
    public <S extends Account> S save(S entity) {
        return accountDao.save(entity);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return accountDao.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return accountDao.existsByUsername(username);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountDao.findById(id).get();
    }

    @Override
    public List<Account> findAllByIsDeleteFalse() {
        return accountDao.findAllByIsDeleteFalse();
    }

    @Override
    public boolean existsById(Long id) {
        return accountDao.existsById(id);
    }
    @Override
    public List<CustomerResponse> findByPhone(CustomerDto accountDto){
        return accountDao.findByPhone(accountDto.getPhone());
    }

    @Override
    public List<Account> findByname(String name) {
        return accountDao.findByNameLike(name);
    }
}
