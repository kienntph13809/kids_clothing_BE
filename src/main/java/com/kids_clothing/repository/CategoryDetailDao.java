package com.kids_clothing.repository;

import com.kids_clothing.entity.Categorydetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryDetailDao extends JpaRepository<Categorydetail, Long> {
    @Query("SELECT b FROM Categorydetail b WHERE b.isDelete = false ")
    List<Categorydetail> listCategoryDetailIsDeleteFalse();

    @Query("SELECT b FROM Categorydetail b WHERE b.id = ?1  AND b.isDelete = false ")
    List<Categorydetail> listCategoryDetailById(Long id);

    List<Categorydetail> findAll();

    List<Categorydetail> findAllByIsDeleteFalse();

    Optional<Categorydetail> findById(Long id);

    Categorydetail saveAndFlush(Categorydetail categoryDetail);

    void delete(Categorydetail categoryDetail);
}
