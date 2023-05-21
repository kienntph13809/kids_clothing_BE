package com.kids_clothing.service.impl;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.ProductRequest;
import com.kids_clothing.repository.ProductDao;
import com.kids_clothing.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

//	@Override
//	public List<Product> findAll() {
//		// TODO Auto-generated method stub
//		return productDao.findAllByIsDeleteFalse();
//	}

    @Override
    public List<Product> findProductNew() {
        // TODO Auto-generated method stub
        return productDao.findProductNew();
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }


    @Override
    public List<Product> findAllByIsDeleteFalse() {
        return productDao.findAllByIsDeleteFalse();
    }

    @Override
    public List<Product> findByDayNewCreate() {
        return productDao.findByDayNewCreate();
    }

    @Override
    public List<ProductRequest> fillallproduc() {
        return productDao.fillallproduc();
    }

    @Override
    public List<ProductRequest> findPriceProduct(String minPrice, String maxPrice, String name ,String nameCate) {
        try {
            if (Objects.equals(minPrice, "") && Objects.equals(maxPrice, "") && Objects.equals(name, "") && Objects.equals(nameCate, "")) {
                return productDao.fillallproduc();
            }
            if (Objects.equals(minPrice, "") && Objects.equals(maxPrice, "")) {
                return productDao.findByDropdownAndName(nameCate,name);
            }
            if (Objects.equals(name, "") && Objects.equals(minPrice, "") && Objects.equals(maxPrice, "")){
                return productDao.findByDropdown(nameCate);
            }
            return productDao.findPriceProduct(minPrice, maxPrice, name , nameCate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product findById(Long id) {
        // TODO Auto-generated method stub
        return productDao.findById(id).get();
    }

    @Override
    public List<Product> findByCategoryDetail(Categorydetail categoryDetail) {
        // TODO Auto-generated method stub
        return productDao.findByCategorydetail(categoryDetail);
    }

    @Override
    public List<ProductRequest> findByNameLike(String name) {
        // TODO Auto-generated method stub
        return productDao.findByNameLike(name);
    }

    @Override
    @Transactional
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return productDao.saveAll(entities);
    }

    @Override
    @Transactional
    public Product saveAndFlush(Product product) {
        // TODO Auto-generated method stub
        return productDao.saveAndFlush(product);
    }

    @Override
    @Transactional
    public void delete(Product product) {
        // TODO Auto-generated method stub
        productDao.delete(product);
        ;
    }

    @Override
    public Product create(Product product) {
        return productDao.save(product);
    }

}
