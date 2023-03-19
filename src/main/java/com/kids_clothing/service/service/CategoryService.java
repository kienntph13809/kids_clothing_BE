package com.kids_clothing.service.service;

import com.kids_clothing.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    
    List<Category> findAllByIsDeleteFalse();

    List<Category> getAllListCategory();

    Optional<Category> findById(Long id);

    List<Category> listCategoryById(Long id);

    List<Category> saveAll(List<Category> category);

    <S extends Category> S save(S entity);

    void delete(Category category);

    void deleteInBatch(List<Category> category);
}
