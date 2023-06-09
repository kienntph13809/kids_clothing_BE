package com.kids_clothing.rest.controller.admin;

import com.kids_clothing.entity.ProductDetail;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Customer/ProductDetailController")
public class ProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("/findAllProductDetai")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<ProductDetail> entity = productDetailService.findAll();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findByIdProduct/{IdProduct}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> finByIdProduct(@PathVariable("IdProduct") long idProduct) {
        List<ProductDetail> entity = productDetailService.findByIdProduct(idProduct);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }
    

    @GetMapping("/findByAlls")
    public ResponseEntity<?> findByAlls(@RequestParam(required = false) Long idProduct, @RequestParam(required = false) Long idSize, @RequestParam(required = false) Long idProperty) {
        if(idSize==null && idProperty==null){
            List<ProductDetail> entitys = productDetailService.findByIdProduct(idProduct);
            return ResponseEntity.ok(new Res(entitys.get(0), "Success", true));
        }
        ProductDetail entity = productDetailService.findByAlls(idProduct,idSize,idProperty);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }


}
