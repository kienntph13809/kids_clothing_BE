package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.Repository.QuantityDao;
import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.ProductDetail;
import com.kids_clothing.model.request.CreateQuantityDto;
import com.kids_clothing.model.request.QuantityDto;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.ProductService;
import com.kids_clothing.service.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Manager/QuantityManagerController")
public class QuantityManagerController {
	@Autowired
	QuantityService quantityService;
	@Autowired
	ProductService productService;

	@Autowired
	QuantityDao quantityDao;

	@GetMapping("/findAllByIsDeleteFalse")
	@PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(new Res(quantityService.findAllByIsDeleteFalse(), "success", true));
	}

	@GetMapping("/findQuantityByProduct/{id}")
	@PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findQuantityByProduct(@PathVariable("id") Long id) {
		Optional<Product> product = Optional.ofNullable(productService.findById(id));
		List<ProductDetail> list = quantityService.findByProduct(product.get());
		return ResponseEntity.ok(new Res(list, "success", true));
	}

	@PostMapping("/saveAndFlush")
	@PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> saveAndFlush(@RequestParam("quantityDTO") String data,
			@RequestParam("status") String status) throws JsonMappingException, JsonProcessingException {
		ProductDetail obj = new ProductDetail();
		ObjectMapper json = new ObjectMapper();
		QuantityDto quantity = json.readValue(data, QuantityDto.class);
		if (status == "CREATE") {
			for (Product x : quantity.getProduct()) {
				ProductDetail entity = quantityService.quantityReady(x.getId(), quantity.getSize().getId(),
						quantity.getProperty().getIdproperty());
				if (entity == null) {
					obj.setIdProduct(x.getId());
					obj.setIdproperty(quantity.getProperty().getIdproperty());
					obj.setIdsize(quantity.getSize().getId());
					obj.setProduct(x);
					obj.setProperty(quantity.getProperty());
					obj.setSize(quantity.getSize());
					obj.setQuantity(Math.round(quantity.getQuantity()));
					obj.setIsDelete(false);
					quantityDao.save(obj);
				} else {
					if (Objects.equals(entity.getIdProduct(), x.getId())
							&& Objects.equals(entity.getIdproperty(), quantity.getProperty().getIdproperty())
							&& Objects.equals(entity.getIdsize(), quantity.getSize().getId())) {
						long updateQuantity = entity.getQuantity() + Math.round(quantity.getQuantity());
						entity.setQuantity(updateQuantity);
						quantityDao.saveAndFlush(entity);
					}
				}
			}
		} else {
			for (Product x : quantity.getProduct()) {
				ProductDetail entity = quantityService.quantityReady(x.getId(), quantity.getSize().getId(),
						quantity.getProperty().getIdproperty());
				if (entity == null) {
					obj.setIdProduct(x.getId());
					obj.setIdproperty(quantity.getProperty().getIdproperty());
					obj.setIdsize(quantity.getSize().getId());
					obj.setProduct(x);
					obj.setProperty(quantity.getProperty());
					obj.setSize(quantity.getSize());
					obj.setQuantity(Math.round(quantity.getQuantity()));
					obj.setIsDelete(false);
					quantityDao.saveAndFlush(obj);
				} else {
					if (Objects.equals(entity.getIdProduct(), x.getId())
							&& Objects.equals(entity.getIdproperty(), quantity.getProperty().getIdproperty())
							&& Objects.equals(entity.getIdsize(), quantity.getSize().getId())) {
						obj.setIdProduct(x.getId());
						obj.setIdproperty(quantity.getProperty().getIdproperty());
						obj.setIdsize(quantity.getSize().getId());
						obj.setProduct(x);
						obj.setProperty(quantity.getProperty());
						obj.setSize(quantity.getSize());
						obj.setQuantity(Math.round(quantity.getQuantity()));
						obj.setIsDelete(false);
						quantityDao.saveAndFlush(obj);
						quantityDao.delete(entity);
					} else {
						entity.setIdProduct(x.getId());
						entity.setIdproperty(quantity.getProperty().getIdproperty());
						entity.setIdsize(quantity.getSize().getId());
						entity.setProduct(x);
						entity.setProperty(quantity.getProperty());
						entity.setSize(quantity.getSize());
						entity.setQuantity(Math.round(quantity.getQuantity()));
						entity.setIsDelete(false);
						entity.setQuantity(Math.round(quantity.getQuantity()));
						quantityDao.saveAndFlush(entity);
					}
				}
			}
		}
		return ResponseEntity.ok(new Res(quantityService.findAll(), "Save success", true));
	}

	/// code chien
	@PostMapping("/createOrUpdate")
	@PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Res> createQty(@RequestBody CreateQuantityDto createQuantity) {
		return ResponseEntity.ok(new Res(quantityService.createQty(createQuantity), "ok", true));
	}

	// xoa quantity
	@GetMapping("/delete/{idqty}")
	@PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Res> deleteQty(@PathVariable("idqty") Long idqty) {
		return ResponseEntity.ok(new Res(quantityService.deleteQty(idqty), "ok", true));
	}

}
