package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/Customer/VoucherController")
public class VoucherController {
    @Autowired
    VoucherService voucherService;

    @GetMapping("/findAllByIsDeleteFalse")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new Res(voucherService.findAllByIsDeleteFalse(), "", true));
    }

    @GetMapping("/findVoucherByAmount")
    public ResponseEntity<?> findVoucherByAmout() {
        return ResponseEntity.ok(new Res(voucherService.findVoucherByAmount(), "", true));
    }
}
