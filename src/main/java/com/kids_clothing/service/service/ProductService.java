package com.kids_clothing.service.service;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> findAll();


    Product findById(Long id);

    List<Product> findProductNew();

    List<Product> findByCategoryDetail(Categorydetail categoryDetail);

    List<ProductRequest> findByNameLike(String name);

    Product saveAndFlush(Product product);

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    void delete(Product product);

    Product create(Product product);

    List<Product> findAllByIsDeleteFalse();

    List<Product> findByDayNewCreate();

    List<ProductRequest> fillallproduc();

    List<ProductRequest> findPriceProduct(String minPrice, String maxPrice, String name , String nameCate);

}