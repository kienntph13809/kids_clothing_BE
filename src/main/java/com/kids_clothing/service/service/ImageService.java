package com.kids_clothing.service.service;

import com.kids_clothing.entity.Image;
import com.kids_clothing.entity.Product;

import java.util.List;

public interface ImageService {
    List<Image> findAll();

    List<Image> findByProduct(Product product);
}
