package com.kids_clothing.service.service;

import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.Quantity;
import com.kids_clothing.model.request.CreateQuantityDto;
import java.util.List;

public interface QuantityService {
    List<Quantity> findAll();
    
    List<Quantity> findAllByIsDeleteFalse();

    List<Quantity> findByProduct(Product product);

    List<Quantity> createQty(CreateQuantityDto createQuantity);

    Quantity quantityReady(Long idproduct, Long idsize, Long idproperty);

    Quantity deleteQty(Long idqty);
}
