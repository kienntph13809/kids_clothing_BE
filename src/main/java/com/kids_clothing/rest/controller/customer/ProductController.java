package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.ProductRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.CategoryDetailService;
import com.kids_clothing.service.service.ImageService;
import com.kids_clothing.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Customer/ProductController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ImageService ImageService;
    @Autowired
    CategoryDetailService categoryDetailService;

    @GetMapping("/home")
    public ResponseEntity<?> findProductNew() {
        List<Product> entity = productService.findProductNew();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findAllByIsDeleteFalse")
    public ResponseEntity<?> findAll() {
        List<Product> entity = productService.findAllByIsDeleteFalse();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findProductById/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") Long id) {
        Product entity = productService.findById(id);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/GetProductByCategory/{id}")
    public ResponseEntity<?> GetProductByCategory(@PathVariable("id") Long id) {
        Optional<Product> product = Optional.ofNullable(productService.findById(id));
        List<Product> entity = productService.findByCategoryDetail(product.get().getCategorydetail());
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<?> GetProductByCategoryDetail(@PathVariable("id") Long id) {
        Optional<Categorydetail> categories = categoryDetailService.findById(id);
        List<Product> entity = productService.findByCategoryDetail(categories.get());
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findByNameLike/{name}")
    public ResponseEntity<?> findByNameLike(@PathVariable("name") String name){
        List<ProductRequest> entity = productService.findByNameLike(name);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }
}
