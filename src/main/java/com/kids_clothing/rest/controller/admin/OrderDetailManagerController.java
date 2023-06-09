package com.kids_clothing.rest.controller.admin;

import com.kids_clothing.entity.Orderdetail;
import com.kids_clothing.repository.OrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Manager/OrderDetailManagerController")
@CrossOrigin("*")

public class OrderDetailManagerController {

    @Autowired
    OrderDetailDao orderDetailDao;

    @GetMapping("/{idbill}")
    public ResponseEntity<List<Orderdetail>> getAllOrderdetail(@PathVariable("idbill") String idbill) {
        return ResponseEntity.ok(orderDetailDao.getListOrderDetail(idbill));

    }


}
