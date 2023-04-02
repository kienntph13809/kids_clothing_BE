package com.kids_clothing.repository;

import com.kids_clothing.entity.Image;
import com.kids_clothing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findAll();

    List<Image> findByProduct(Product product);

    @Query("Select b FROM Image b WHERE b.product.id = ?1 AND b.isDelete = False")
    List<Image> ListImagesByProduct(Long idProduct);


}
