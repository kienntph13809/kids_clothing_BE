package com.kids_clothing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "categorydetail")
public class Categorydetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "isDelete")
    private Boolean isDelete = false;
    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "categorydetail")
    private List<Product> products;


}
