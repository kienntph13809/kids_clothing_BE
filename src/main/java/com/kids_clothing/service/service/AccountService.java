package com.kids_clothing.service.service;

import java.util.List;

import com.kids_clothing.entity.Account;

public interface AccountService {
    boolean existsById(Long id);

    List<Account> findAll();

    <S extends Account> S save(S entity);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Account findById(Long id);

    List<Account> findAllByIsDeleteFalse();

}
