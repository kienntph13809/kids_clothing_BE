package com.kids_clothing.service.impl;

import com.kids_clothing.entity.Role;
import com.kids_clothing.repository.RoleDao;
import com.kids_clothing.service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public Optional<Role> findByName(String name) {
        return roleDao.findByName(name);
    }
}
