package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Image;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.ImageService;
import com.kids_clothing.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Customer/ImageController")
public class ImageController {
    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;

    @GetMapping("/findByProduct/{productId}")
    public ResponseEntity<?> findByProduct(@PathVariable("productId") Long id) {
        Product product = productService.findById(id);
        List<Image> entity = imageService.findByProduct(product);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

}
