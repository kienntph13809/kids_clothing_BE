package com.kids_clothing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;//giá gốc

    private Double discount;//giảm %

    @Length(max = 5000)
    private String description;//mô tả

    @Length(max = 5000)
    private String descriptionDetail;//mô tả chi tiết

    private String status;//trạng thái

    private Date day_update;

    private String image;//hình ảnh

    @Column(name = "isDelete")
    private Boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "idcategorydetail")
    private Categorydetail categorydetail;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Image> list_Images;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Quantity> quantities;

}
