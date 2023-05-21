package com.kids_clothing.repository;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.model.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query(value = "SELECT product.name, product.image, product.price, productdetail.idproduct, categorydetail.name " +
            "AS categorydetail_name, categorydetail.id AS categorydetail_id, category.name " +
            "AS category_name, category.id AS category_id, " +
            "SUM(productdetail.quantity) AS quantity" +
            "FROM productdetail JOIN product ON product.id = productdetail.idproduct " +
            "JOIN categorydetail ON product.idcategorydetail = categorydetail.id " +
            "JOIN category ON categorydetail.idcategory = category.id " +
            "WHERE categorydetail.name LIKE %:name% " +
            "GROUP BY product.id", nativeQuery = true)
    List<ProductRequest> findByDropdown(@Param("name") String name);
}
