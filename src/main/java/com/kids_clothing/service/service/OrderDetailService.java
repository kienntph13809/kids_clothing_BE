package com.kids_clothing.service.service;

import com.kids_clothing.entity.Orderdetail;

import java.util.List;

public interface OrderDetailService {
    List<Orderdetail> AllByCustomer(Long id);

    List<Orderdetail> saveAll(List<Orderdetail> orderdetail);

    List<Orderdetail> FinAll();
}
