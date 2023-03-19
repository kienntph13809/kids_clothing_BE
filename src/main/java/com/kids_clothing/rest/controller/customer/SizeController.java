package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Size;
import com.kids_clothing.entity.Typesize;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.SizeService;
import com.kids_clothing.service.service.TypeSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Customer/SizeController")
public class SizeController {
    @Autowired
    SizeService sizeService;
    @Autowired
    TypeSizeService typeService;

    @GetMapping("/findAllByIsDeleteFalse")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new Res(sizeService.findAllByIsDeleteFalse(), "OK", true));
    }

    @PostMapping("/findByTypeSize")
    public ResponseEntity<?> findByTypeSize(@RequestBody String id) {
        long l = Long.parseLong(id);
        Optional<Typesize> entity = Optional.ofNullable(typeService.findById(l).get());
        List<Size> list = sizeService.findByTypeSize(entity.get());
        return ResponseEntity.ok(new Res(list, "OK", true));
    }
}
