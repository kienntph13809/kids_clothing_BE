package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.controllers.BaseController;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.vnpay.MamiPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer/MamiPayController")
@CrossOrigin("*")
public class MamiPayController extends BaseController {

    @Autowired
    MamiPayService mamiPayService;

    @PostMapping("/create")
    public ResponseEntity<Res> creteMamiPay() {
        return ResponseEntity.ok(new Res(mamiPayService.createMamiPay(getAuthUID()), "Thành công", true));
    }

    @GetMapping("/mamipay")
    public ResponseEntity<Res> getMamiPay() {
        return ResponseEntity.ok(new Res(mamiPayService.getMamiPayCustomer(getAuthUID()), "Thành công", true));
    }

}
