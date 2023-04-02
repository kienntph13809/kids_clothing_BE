package com.kids_clothing.repository;

import com.kids_clothing.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {
    @Query("SELECT b FROM Category b WHERE b.isDelete = false ")
    List<Category> listCategoriesIsDeleteTrue();

    @Query("SELECT b FROM Category b WHERE b.id = ?1  AND b.isDelete = false ")
    List<Category> listCategoryById(Long id);

    List<Category> findAll();

    Category saveAndFlush(Category category);

    void delete(Category category);

    List<Category> findAllByIsDeleteFalse();


}
