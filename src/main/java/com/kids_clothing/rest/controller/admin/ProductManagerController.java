package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.ProductRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.CategoryDetailService;
import com.kids_clothing.service.service.ImageService;
import com.kids_clothing.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Manager/ProductManagerController")
public class ProductManagerController {
    private final Path root = Paths.get("severImg");
    public String upload;
    @Autowired
    ServletContext application;
    @Autowired
    ProductService productService;
    @Autowired
    ImageService ImageService;
    @Autowired
    CategoryDetailService categoryDetailService;

    @GetMapping("/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<Product> entity = productService.findAllByIsDeleteFalse();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findProductById/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findProductById(@PathVariable("id") Long id) {
        Product entity = productService.findById(id);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @PostMapping("/saveAndFlush")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveAndFlush(@RequestParam(required = false, value = "files") MultipartFile file,
                                          @RequestParam(required = false, value = "Product") String data) {
        try {
            ObjectMapper json = new ObjectMapper();
            Product product = json.readValue(data, Product.class);
            if (file != null) {
                String filename = file.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                filename = uuid.toString() + ".jpg";
                Files.copy(file.getInputStream(), this.root.resolve(filename));
                product.setImage("http://localhost:8080/manager/image/get/" + filename);
            }
            product.setIsDelete(false);
            Product entity = productService.saveAndFlush(product);
            return ResponseEntity.ok(new Res(productService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product) {
        try {
            product.setIsDelete(true);
            productService.saveAndFlush(product);
            return ResponseEntity.ok(new Res(productService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }

    @GetMapping("/findByDayNewCreate")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findByDayNewCreate() {
        List<Product> entity = productService.findByDayNewCreate();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findAllproduc")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllproduc() {
        List<ProductRequest> entity = productService.fillallproduc();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findPriceProduc")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findPriceProduc(@RequestParam(required = false) String minPrice,
                                             @RequestParam(required = false) String maxPrice,
                                             @RequestParam(required = false) String name
                                             ) {

        List<ProductRequest> entity = productService.findPriceProduct(minPrice, maxPrice, name);
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findby_name")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findbyname(@RequestParam("name") String name) {
        return ResponseEntity.ok(new Res(productService.findByNameLike(name), "thành công", true));
    }

}
