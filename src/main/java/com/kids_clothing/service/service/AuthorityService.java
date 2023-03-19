package com.kids_clothing.service.service;

import java.util.List;

import com.kids_clothing.entity.Authority;

public interface AuthorityService {
	List<Authority> findAllByIsDeleteFalse();

	List<Authority> saveAll(List<Authority> authority);
}
