package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.ProductDetail;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.ProductService;
import com.kids_clothing.service.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Customer/QuantityController")
public class QuantityController {

	@Autowired
	QuantityService quantityService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/findAllByIsDeleteFalse")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(new Res(quantityService.findAllByIsDeleteFalse(),"success",true));
	}
	@GetMapping("/findQuantityByProduct/{id}")
	public ResponseEntity<?> findQuantityByProduct(@PathVariable("id") Long id){
		Optional<Product> product = Optional.ofNullable(productService.findById(id));
		List<ProductDetail> list = quantityService.findByProduct(product.get());
		return ResponseEntity.ok(new Res(list,"success",true));
	}

}
