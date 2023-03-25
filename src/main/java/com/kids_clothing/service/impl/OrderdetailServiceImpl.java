package com.kids_clothing.service.impl;

import com.kids_clothing.Repository.OrderDetailDao;
import com.kids_clothing.entity.Orderdetail;
import com.kids_clothing.service.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderdetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Override
    public List<Orderdetail> AllByCustomer(Long id) {
        return orderDetailDao.AllByCustomer(id);
    }

    @Override
    public List<Orderdetail> saveAll(List<Orderdetail> orderdetail) {
        return orderDetailDao.saveAll(orderdetail);
    }

    @Override
    public List<Orderdetail> FinAll() {
        return orderDetailDao.findAll();
    }
}
