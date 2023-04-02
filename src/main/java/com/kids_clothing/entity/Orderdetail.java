package com.kids_clothing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "orderdetail")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orderdetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;//giá gốc

    private Double downprice;//giá giảm

    private Long quantitydetail;//số lượng mua

    private Double intomoney;//thành tiền

    @Column(name = "create_at")
    private Date createAt = new Date();

    @Column(name = "isDelete")
    @Builder.Default
    private Boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "idproductDetail", updatable = false, insertable = false)
    private ProductDetail productDetail;

    @Column(name = "idproductDetail")
    private Long idproductDetail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idbill", updatable = false, insertable = false)
    private Bill bill;

    @Column(name = "idbill")
    private String idbill;

}
