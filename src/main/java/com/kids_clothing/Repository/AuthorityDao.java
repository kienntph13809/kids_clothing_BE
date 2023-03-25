package com.kids_clothing.Repository;

import com.kids_clothing.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
    List<Authority> findAllByIsDeleteFalse();
}
