package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.entity.Orderdetail;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.repository.BillDao;
import com.kids_clothing.repository.OrderDetailDao;
import com.kids_clothing.service.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Customer/OrderDetailController")
@CrossOrigin("*")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    BillDao billDao;


    @GetMapping("/list")
    public ResponseEntity<List<Orderdetail>> listOrderdetail() {
        return ResponseEntity.ok(orderDetailService.FinAll());
    }

    @PostMapping("{id}")
    public ResponseEntity<?> AllByCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Res(orderDetailService.AllByCustomer(id), "ok", true));
    }

    @GetMapping("/{idbill}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Orderdetail>> findByIdBill(@PathVariable("idbill") String idbill) {
        return ResponseEntity.ok(orderDetailDao.getListOrderDetail(idbill));

    }

    @PostMapping("/update/{idbill}/{idOrder}/{newQuantity}")
    public ResponseEntity<?> updateQuantity(@PathVariable("idbill") String idbill,
                                            @PathVariable("newQuantity") Long newQuantity,
                                            @PathVariable("idOrder") Long idOrder) {
      Optional<Orderdetail> opOrder =  orderDetailDao.findById(idOrder);
      Orderdetail order = opOrder.get();
      order.setDownprice(Double.valueOf(1111));
        orderDetailDao.updateQuantitydetailByBillId(newQuantity,idbill,idOrder);
        return ResponseEntity.ok(new Res(orderDetailDao.getListOrderDetail(idbill),"update oK",true));

    }

    @PostMapping("/update_address/{idBill}/{addressNew}")
    public ResponseEntity<?> updateAddress( @PathVariable("addressNew") String addressNew,
                                            @PathVariable("idBill") String idBill) {
        billDao.updateAddressByBillId(addressNew,idBill);
        System.out.println(addressNew);
        return ResponseEntity.ok(new Res("update oK",true));

    }

}
