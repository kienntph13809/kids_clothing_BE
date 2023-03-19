package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Property;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Customer/PropertyController")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/findAllByIsDeleteFalse")
    public ResponseEntity<?> findAllByIsDeleteFalse() {
        List<Property> entity = propertyService.findAll();
        if (entity.size() > 0)
            return ResponseEntity.ok(new Res(propertyService.findAllByIsDeleteFalse(), "OK", true));
        else
            return ResponseEntity.ok(new Res("Not OK", false));
    }
}
