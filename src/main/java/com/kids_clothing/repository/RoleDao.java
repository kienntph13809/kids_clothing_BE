package com.kids_clothing.repository;

import com.kids_clothing.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
