package com.kids_clothing.dao;

import com.kids_clothing.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDao extends JpaRepository<Property, Long> {
    List<Property> findAll();
    List<Property> findAllByIsDeleteFalse();
}
