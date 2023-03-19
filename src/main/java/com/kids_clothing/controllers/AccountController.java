package com.kids_clothing.controllers;

import com.kids_clothing.entity.Account;
import com.kids_clothing.response.dto.Res;
import com.kids_clothing.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(value = "")
    public ResponseEntity<List<Account>> getUserList(Model model) {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") long id) {
        if (!accountService.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new Res(accountService.findById(id), "thong tin tai khoan", true));
    }

    @PostMapping(value = "")
    public ResponseEntity<Account> post(@RequestBody Account account) {
        if (accountService.existsById(account.getId())) {
            return ResponseEntity.badRequest().build();
        }
        accountService.save(account);
        return ResponseEntity.ok(account);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account account) {
        if (!accountService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountService.save(account);
        return ResponseEntity.ok(account);

    }
}
