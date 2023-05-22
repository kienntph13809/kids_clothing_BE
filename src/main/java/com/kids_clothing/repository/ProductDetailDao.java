package com.kids_clothing.repository;


import com.kids_clothing.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailDao extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findAll();

    List<ProductDetail> findByIdProduct(long idProduct);
    @Query("SELECT pro FROM ProductDetail pro  where pro.idProduct =?1 and pro.idsize =?2 and pro.idproperty=?3")
    ProductDetail findByAlls(Long idProduct, Long idSize, Long idProperty);
}
