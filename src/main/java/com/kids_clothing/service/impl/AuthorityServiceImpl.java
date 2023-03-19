package com.kids_clothing.service.impl;

import com.kids_clothing.dao.AuthorityDao;
import com.kids_clothing.entity.Authority;
import com.kids_clothing.service.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityDao authorityDao;

	@Override
	public List<Authority> findAllByIsDeleteFalse() {
		// TODO Auto-generated method stub
		return authorityDao.findAllByIsDeleteFalse();
	}
	
	@Override
    @Transactional
    public List<Authority> saveAll(List<Authority> authority) {
        // TODO Auto-generated method stub
        return authorityDao.saveAll(authority);
    }

}
