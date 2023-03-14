package com.kids_clothing.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "voucher")
public class Voucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private Double discount;

    @Column(name = "name")
    private String name;

    @Column(name = "minimumValue")
    private Double minimumValue;
    @Column(name = "isDelete")
    private Boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "idevent", updatable = false, insertable = false)
    private Event event;

    @Column(name = "idevent")
    private Long idevent;

//	@JsonIgnore
//	@OneToMany(mappedBy = "voucher")
//	private List<Bill> bills;


}
