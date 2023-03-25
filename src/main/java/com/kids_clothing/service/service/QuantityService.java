package com.kids_clothing.service.service;

import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.ProductDetail;
import com.kids_clothing.model.request.CreateQuantityDto;
import java.util.List;

public interface QuantityService {
    List<ProductDetail> findAll();
    
    List<ProductDetail> findAllByIsDeleteFalse();

    List<ProductDetail> findByProduct(Product product);

    List<ProductDetail> createQty(CreateQuantityDto createQuantity);

    ProductDetail quantityReady(Long idproduct, Long idsize, Long idproperty);

    ProductDetail deleteQty(Long idqty);
}
