package com.kids_clothing.service.impl;

import com.kids_clothing.entity.ProductDetail;
import com.kids_clothing.repository.ProductDetailDao;
import com.kids_clothing.service.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailImpl implements ProductDetailService {
    @Autowired
    ProductDetailDao productDetailDao;

    @Override
    public List<ProductDetail> findAll() {
        return productDetailDao.findAll();
    }

    @Override
    public List<ProductDetail> findByIdProduct(long idProduct) {
        return productDetailDao.findByIdProduct(idProduct);
    }
}
