package com.kids_clothing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "size")
public class Size implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "idtypesize", updatable = false, insertable = false)
    private Typesize typesize;

    @Column(name = "idtypesize")
    private Long idtypesize;

    @Column(name = "isDelete")
    private Boolean isDelete = false;

    @JsonIgnore
    @OneToMany(mappedBy = "size")
    private List<ProductDetail> ProductDetails;
}
