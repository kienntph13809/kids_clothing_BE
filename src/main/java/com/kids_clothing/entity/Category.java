package com.kids_clothing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "isDelete")
    private Boolean isDelete = false;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Categorydetail> categorydetails;
}
