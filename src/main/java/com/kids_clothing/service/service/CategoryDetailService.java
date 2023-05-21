package com.kids_clothing.service.service;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.model.request.ProductRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryDetailService {
    List<Categorydetail> findAll();

    List<Categorydetail> findAllByIsDeleteFalse();

    Optional<Categorydetail> findById(Long id);

    List<Categorydetail> getAllListDetailCategory();

    List<Categorydetail> listCategoryDetailById(Long id);

    Categorydetail saveAndFlush(Categorydetail categoryDetail);

    List<Categorydetail> saveAll(List<Categorydetail> categoryDetail);

    void delete(Categorydetail categoryDetail);

    void deleteInBatch(List<Categorydetail> categoryDetail);


    List<ProductRequest> findByDropdown(String name);
}

