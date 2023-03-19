package com.kids_clothing.rest.controller.admin;

import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Customer;
import com.kids_clothing.model.request.CustomerDto;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.AccountService;
import com.kids_clothing.service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Manager/CustomersManagerController")
public class CustomersManagerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;

    @GetMapping("/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {
        List<Customer> customer = customerService.findAllFalse();
        return ResponseEntity.ok(new Res(customer, "dat", true));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> getDetail(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);
        if (customer.getIsDelete() == true || customer.getAccount().getIsDelete() == true) {
            return ResponseEntity.ok(new Res(null, "Đã bị xóa", false));
        }
        return ResponseEntity.ok(new Res(customer, "Thanh cong", true));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CustomerDto customerDto) {
        Account ac = accountService.findById(customerDto.getIdaccount());
        Customer ct = customerService.findById(id);
        if (ct.getIsDelete() == true) {
            return ResponseEntity.ok(new Res(null, "Đã bị xóa update lỗi", false));
        }
        Customer cts = new Customer();
        cts.setId(id);
        cts.setFullname(customerDto.getFullname());
        cts.setStatuscustomer(customerDto.getStatuscustomer());
        cts.setAccount(ac);
        Customer customer = customerService.update(cts);
        return ResponseEntity.ok(new Res(customer, "update t", true));
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> delete(@PathVariable("id") Long id) {
        Customer ct = customerService.findById(id);
        if (ct != null) {
            ct.setIsDelete(true);
        }
        Customer customer = customerService.update(ct);
        return ResponseEntity.ok(new Res(customer, "xóa thành công", true));
    }
}
