package com.kids_clothing.repository;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p ORDER BY day_update DESC")
    List<Product> findProductNew();

    List<Product> findByCategorydetail(Categorydetail categorydetail);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameLike(@Param("name") String name);

    Product saveAndFlush(Product product);

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    void delete(Product product);

    void deleteInBatch(Iterable<Product> product);

    List<Product> findAllByIsDeleteFalse();

    @Query("SELECT p FROM Product p ORDER BY p.day_update DESC")
    List<Product> findByDayNewCreate();
}
