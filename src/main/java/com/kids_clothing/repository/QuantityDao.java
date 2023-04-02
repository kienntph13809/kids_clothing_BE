package com.kids_clothing.repository;

import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuantityDao extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findAll();

    List<ProductDetail> findByProductAndIsDeleteFalse(Product product);

    List<ProductDetail> findAllByIsDeleteFalse();

    @Query("select q from ProductDetail q " +
            " where q.idsize = ?1 AND q.idproperty = ?2 and q.idProduct = ?3")
    Optional<ProductDetail> checkQty(Long idsize, Long idproperty, Long idProduct);


}
