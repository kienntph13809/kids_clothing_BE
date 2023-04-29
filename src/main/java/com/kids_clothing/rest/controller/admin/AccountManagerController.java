package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.repository.AccountDao;
import com.kids_clothing.repository.AuthorityDao;
import com.kids_clothing.repository.RoleDao;
import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Authority;
import com.kids_clothing.entity.Role;
import com.kids_clothing.model.request.AccountDto;
import com.kids_clothing.model.request.CustomerDto;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.AccountService;
import com.kids_clothing.service.service.AuthorityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/Manager/AccountManagerController")
public class AccountManagerController {
    @Autowired
    AccountService accountService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    RoleDao roleDAO;
    @Autowired
    AccountDao accountDAO;
    @Autowired
    AuthorityDao authorityDAO;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping(value = "/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserList() {
        return ResponseEntity.ok(new Res(authorityService.findAllByIsDeleteFalse(), "Save Success", true));
    }
    
    @GetMapping(value = "/findAll")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new Res(roleDAO.findAll(), "Save Success", true));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable("id") long id) {
        if (!accountService.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new Res(accountService.findById(id), "thong tin tai khoan", true));
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@RequestBody Authority authority) {
        try {
        	authority.setIsDelete(true);
            authorityDAO.saveAndFlush(authority);
            return ResponseEntity.ok(new Res(accountService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }
    @PostMapping(value = "/updateInline")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems,
                                          BindingResult bindingResult) {
        try {
        	if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.NOT_MODIFIED);
            }
            ObjectMapper json = new ObjectMapper();
            List<Authority> created;
            List<Authority> updated;
            List<Authority> deleted;

            created = Arrays.asList(json.readValue(createdItems, Authority[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Authority[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Authority[].class));

            if (created.size() > 0) {
                for (Authority entity : created) {
                	 if (accountDAO.existsByUsername(entity.getAccount().getUsername())) {
                         return ResponseEntity.badRequest().body(new Res("Username đã tồn tại", false));
                     }
                     if (accountDAO.existsByEmail(entity.getAccount().getEmail())) {
                         return ResponseEntity.badRequest().body(new Res("Email đã tồn tại", false));
                     }
                     Account account = new Account();
                     BeanUtils.copyProperties(entity.getAccount(), account);
                     account.setPassword(encoder.encode(entity.getAccount().getPassword()));
                     Role roles = entity.getRole();
                     accountDAO.saveAndFlush(account);
                     authorityDAO.saveAndFlush(entity);
                }
            }
            if (updated.size() > 0) {
                for (Authority entity : updated) {
                	Account account = new Account();
                    BeanUtils.copyProperties(entity.getAccount(), account);
                    account.setPassword(entity.getAccount().getPassword());
                	entity.setAccount(entity.getAccount());
                    accountDAO.saveAndFlush(account);
                    authorityDAO.saveAndFlush(entity);
                }
            }
            if (deleted.size() > 0) {
                for (Authority entity : deleted) {
                    entity.setIsDelete(true);
                }
                authorityDAO.saveAll(deleted);
//				categoryDetailService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(accountService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }

    @PostMapping(value = "/findByPhone")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOne(@RequestBody CustomerDto customer) {
        return ResponseEntity.ok(new Res(accountService.findByPhone(customer),"success", true));
    }
    @GetMapping("/findby_name")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findbyname(@RequestParam("name") String name) {
        return ResponseEntity.ok(new Res(accountService.findByname(name), "thành công", true));
    }

}
