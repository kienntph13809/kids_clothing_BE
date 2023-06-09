package com.kids_clothing.rest.controller.admin;

import com.kids_clothing.model.response.Res;
import com.kids_clothing.repository.MamiPayDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Manager/OrderDetailManagerController")
@CrossOrigin("*")

public class MamiPayManagerController {

    @Autowired
    MamiPayDao mamiPayDao;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> getMamiPay() {
        return ResponseEntity.ok(new Res(mamiPayDao.findAll(), "Thành công", true));
    }

}
