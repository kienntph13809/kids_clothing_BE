package com.kids_clothing.repository;


import com.kids_clothing.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailDao extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findAll();

    List<ProductDetail> findByIdProduct(long idProduct);
}
