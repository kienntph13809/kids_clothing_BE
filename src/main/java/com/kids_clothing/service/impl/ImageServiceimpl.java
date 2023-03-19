package com.kids_clothing.service.impl;

import com.kids_clothing.dao.ImageDao;
import com.kids_clothing.entity.Image;
import com.kids_clothing.entity.Product;
import com.kids_clothing.service.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceimpl implements ImageService {

    @Autowired
    ImageDao imageDao;

    @Override
    public List<Image> findAll() {
        // TODO Auto-generated method stub
        return imageDao.findAll();
    }

    @Override
    public List<Image> findByProduct(Product product) {
        // TODO Auto-generated method stub
        return imageDao.findByProduct(product);
    }

}
