package com.kids_clothing.service.service;

import com.kids_clothing.entity.Orderdetail;
import com.kids_clothing.repository.OrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderDetailService {
    List<Orderdetail> AllByCustomer(Long id);

    List<Orderdetail> saveAll(List<Orderdetail> orderdetail);

    List<Orderdetail> FinAll();

    @Service
    class OrderdetailServiceImpl implements OrderDetailService {

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
}
