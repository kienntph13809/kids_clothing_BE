package com.kids_clothing.model.request;

import com.kids_clothing.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {

    private Long id;

    private Double price;

    private Double downprice;

    private Long quantitydetail;

    private String idbill;

    private Double intomoney;

    private ProductDetail productDetail;


}
