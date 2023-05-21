package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Customer;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.AccountService;
import com.kids_clothing.service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/Customer/CustomerController")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> findByAccount(@PathVariable("id") Long id) {
        Customer customer = customerService.findByAccount(id);
        if (customer.getIsDelete() == true || customer.getAccount().getIsDelete() == true) {
            return ResponseEntity.ok(new Res(null, "Đã bị xóa", false));
        }
        return ResponseEntity.ok(new Res(customer, "Thanh cong", true));
    }

}
