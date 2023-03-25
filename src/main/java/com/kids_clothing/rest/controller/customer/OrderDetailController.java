package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.Repository.OrderDetailDao;
import com.kids_clothing.entity.Orderdetail;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Customer/OrderDetailController")
@CrossOrigin("http://localhost:4200/")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderDetailDao orderDetailDao;


    @GetMapping("/list")
    public ResponseEntity<List<Orderdetail>> listOrderdetail() {
        return ResponseEntity.ok(orderDetailService.FinAll());
    }

    @PostMapping("{id}")
    public ResponseEntity<?> AllByCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Res(orderDetailService.AllByCustomer(id), "dat", true));
    }

    @GetMapping("/{idbill}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Orderdetail>> findByIdBill(@PathVariable("idbill") String idbill) {
        return ResponseEntity.ok(orderDetailDao.getListOrderDetail(idbill));

    }

}
