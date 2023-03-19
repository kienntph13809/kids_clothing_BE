package com.kids_clothing.rest.controller.admin;

import com.kids_clothing.dao.OrderDetailDao;
import com.kids_clothing.entity.Orderdetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Manager/OrderDetailManagerController")
@CrossOrigin("http://localhost:4200/")

public class OrderDetailManagerController {

    @Autowired
    OrderDetailDao orderDetailDao;

    @GetMapping("/{idbill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Orderdetail>> getAllOrderdetail(@PathVariable("idbill") String idbill) {
        return ResponseEntity.ok(orderDetailDao.getListOrderDetail(idbill));

    }


}
