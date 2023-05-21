package com.kids_clothing.service.service;

import com.kids_clothing.entity.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findAllByIsDeleteFalse();

    List<Authority> saveAll(List<Authority> authority);
}
