package com.kids_clothing.service.service;

import com.kids_clothing.entity.Property;

import java.util.List;

public interface PropertyService {
    List<Property> findAll();

    List<Property> saveAll(List<Property> property);

    List<Property> findAllByIsDeleteFalse();

    void deleteInBatch(List<Property> property);
}
