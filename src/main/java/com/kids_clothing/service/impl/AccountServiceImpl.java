package com.kids_clothing.service.impl;

import java.util.List;

import com.kids_clothing.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kids_clothing.entity.Account;
import com.kids_clothing.service.service.AccountService;

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

}
