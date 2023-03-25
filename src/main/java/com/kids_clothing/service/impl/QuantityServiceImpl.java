package com.kids_clothing.service.impl;

import com.kids_clothing.Repository.QuantityDao;
import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.ProductDetail;
import com.kids_clothing.model.request.CreateQuantityDto;
import com.kids_clothing.service.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuantityServiceImpl implements QuantityService {

    @Autowired
    QuantityDao quantityDao;

    @Override
    public List<ProductDetail> findAll() {
        // TODO Auto-generated method stub
        return quantityDao.findAll();
    }

    @Override
    public List<ProductDetail> findByProduct(Product product) {
        // TODO Auto-generated method stub
        return quantityDao.findByProductAndIsDeleteFalse(product);
    }

    @Override
	public List<ProductDetail> findAllByIsDeleteFalse() {
		// TODO Auto-generated method stub
		return quantityDao.findAllByIsDeleteFalse();
	}
    
    @Override
    public List<ProductDetail> createQty(CreateQuantityDto createQuantity) {
        List<ProductDetail> quantities = new ArrayList<>();
        createQuantity.getPropertyrequests().forEach(property -> {
            ProductDetail productDetail = new ProductDetail();
            Optional<ProductDetail> qty = quantityDao.checkQty(createQuantity.getIdsize(), property.getIdproperty(), createQuantity.getIdpoduct());
            if (qty.isPresent()) {
                productDetail = qty.get();
            } else {
                productDetail.setIdProduct(createQuantity.getIdpoduct());
                productDetail.setIdsize(createQuantity.getIdsize());
                productDetail.setIdproperty(property.getIdproperty());
            }
            productDetail.setIsDelete(false);
            productDetail.setQuantity(property.getQuantity());

            quantities.add(productDetail);

        });
        return quantityDao.saveAll(quantities);
    }

    @Override
    public ProductDetail deleteQty(Long idqty) {
        ProductDetail productDetail = quantityDao.findById(idqty).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy quantity");
        });
        productDetail.setIsDelete(true);

        return quantityDao.save(productDetail);
    }

    @Override
    public ProductDetail quantityReady(Long idproduct, Long idsize, Long idproperty) {
        Optional<ProductDetail> quantity = quantityDao.checkQty(idsize, idproperty, idproduct);
        return quantity.orElse(null);
    }

}
