package com.kids_clothing.service.service;

import com.kids_clothing.entity.Account;
import com.kids_clothing.model.request.CustomerDto;
import com.kids_clothing.model.request.CustomerRequestt;
import com.kids_clothing.model.request.requestAccount;
import com.kids_clothing.model.response.CustomerResponse;

import java.util.List;

public interface AccountService {
    boolean existsById(Long id);

    List<Account> findAll();

    <S extends Account> S save(S entity);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Account findById(Long id);

    List<Account> findAllByIsDeleteFalse();

    List<CustomerResponse> findByPhone(CustomerDto accountDto);

    List<CustomerRequestt> findByname(String name);

    List<requestAccount> findAllAdmin();
    List<CustomerRequestt> findAllCustomer();

}
