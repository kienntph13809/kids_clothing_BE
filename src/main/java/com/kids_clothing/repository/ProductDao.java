package com.kids_clothing.repository;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.ProductRequest;
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

    @Query(value = "SELECT product.name,product.image,product.price, productdetail.idproduct,categorydetail.name as categorydetail_name,categorydetail.id as categorydetail_id,\n" +
            "category.name AS category_name,category.id as category_id,\n" +
            "SUM(productdetail.quantity) as quantity FROM productdetail JOIN product \n" +
            "on product.id = productdetail.idproduct JOIN categorydetail on product.idcategorydetail = categorydetail.id\n" +
            "JOIN category ON categorydetail.idcategory = category.id\n" +
            "GROUP BY productdetail.idproduct", nativeQuery = true)
    List<ProductRequest> fillallproduc();
}
