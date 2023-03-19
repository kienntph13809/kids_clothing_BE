package com.kids_clothing.dao;

import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuantityDao extends JpaRepository<Quantity, Long> {
    List<Quantity> findAll();

    List<Quantity> findByProductAndIsDeleteFalse(Product product);

    List<Quantity> findAllByIsDeleteFalse();

    @Query("select q from Quantity q " +
            " where q.idsize = ?1 AND q.idproperty = ?2 and q.idProduct = ?3")
    Optional<Quantity> checkQty(Long idsize, Long idproperty, Long idProduct);


}
