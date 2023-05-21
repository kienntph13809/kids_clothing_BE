package com.kids_clothing.rest.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.repository.AccountDao;
import com.kids_clothing.repository.CustomerDao;
import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Customer;
import com.kids_clothing.model.request.ChangeAccountDto;
import com.kids_clothing.model.request.SignupRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")

public class AccountsController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AccountService accountService;
    @Autowired
    CustomerDao customerDAO;
    @Autowired
    AccountDao accountDAO;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {
        List<Account> account = accountService.findAllByIsDeleteFalse();
        return ResponseEntity.ok(new Res(account, "dat", true));
    }
   
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> getDetail(@PathVariable("id") Long id) {
        Account account = accountService.findById(id);
        if (account.getIsDelete()) {
            return ResponseEntity.ok(new Res(null, "Đã bị xóa", false));
        }
        return ResponseEntity.ok(new Res(account, "Thanh cong", true));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SignupRequest accounts) {
        Account ac = accountService.findById(id);
        if (ac.getIsDelete()) {
            return ResponseEntity.ok(new Res(null, "Đã bị xóa update lỗi", false));
        }
        ac.setId(id);
        ac.setEmail(accounts.getEmail());
        Account acs = accountService.save(ac);
        return ResponseEntity.ok(new Res(acs, "update t", true));
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> delete(@PathVariable("id") Long id) {
        Account ac = accountService.findById(id);
        if (ac != null) {
            ac.setIsDelete(true);
        }
        Account account = accountService.save(ac);
        return ResponseEntity.ok(new Res(account, "xóa thành công", true));
    }
    
    @PostMapping("/UpdateAccount")
    public ResponseEntity<?> saveAccount(@RequestBody ChangeAccountDto dto){
    	Customer customer = customerDAO.findByIdaccount(dto.getId());
    	Account account = accountService.findById(dto.getId());
    	if(customer != null) {
    		customer.setFullname(dto.getFullname());
            customer.setAddress(dto.getAddress());
    		customerDAO.saveAndFlush(customer);
    	}
    	if(account != null) {
    		account.setEmail(dto.getEmail());
    		account.setUsername(dto.getUsername());
    		account.setPhone(dto.getSdt());
    		accountDAO.saveAndFlush(account);
    	}
    	return ResponseEntity.ok(new Res(null,"Save success",true));
    }
    @GetMapping("/findby_name")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findbyname(@RequestParam("name") String name) {
        return ResponseEntity.ok(new Res(accountService.findByname(name), "thành công", true));
    }
}
