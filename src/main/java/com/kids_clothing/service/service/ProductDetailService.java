package com.kids_clothing.service.service;

import com.kids_clothing.entity.ProductDetail;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> findAll();

    List<ProductDetail> findByIdProduct(long idProduct);

    ProductDetail findByAlls(Long idProduct, Long idSize, Long idProperty);

}
