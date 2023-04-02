
package com.kids_clothing.rest.controller.customer;

import com.kids_clothing.controllers.BaseController;
import com.kids_clothing.repository.AccountDao;
import com.kids_clothing.repository.QuantityDao;
import com.kids_clothing.entity.Bill;

import com.kids_clothing.model.request.BillDto;
import com.kids_clothing.model.request.UpdateBillCutomer;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.BillService;
import com.kids_clothing.service.service.CustomerService;
import com.kids_clothing.service.service.OrderDetailService;
import com.kids_clothing.vnpay.MamipayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin("http://localhost:4200/")
public class BillController extends BaseController {

    @Autowired
    BillService billService;
    @Autowired
    AccountDao accountDao;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    MamipayServiceImpl mamipayService;
    @Autowired
    CustomerService customerService;

    @Autowired
    QuantityDao quantityDao;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> BillByCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Res(billService.BillByCustomer(id), "Thông tin đơn hàng", true));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Bill>> listBill() {
        return ResponseEntity.ok(billService.findAllCustomer());
    }

    @PostMapping("/creat")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createBill(@RequestBody BillDto billDto) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(new Res(billService.create(billDto), "oke", true));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Res> updateBill(@RequestBody UpdateBillCutomer updateBillCutomer, @PathVariable("id") String idbill) {
        return ResponseEntity.ok(new Res(billService.updateBillCustomer(updateBillCutomer, idbill), "ok", true));
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Res> cancelBill(@PathVariable("id") String idbill) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(new Res(billService.cancelBill(idbill), "Đã Hủy", true));
    }

    @GetMapping("/shiping/{idBill}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Res> shipingBill(@PathVariable("idBill") String idBill) throws IOException {
        return ResponseEntity.ok(new Res(billService.getShipingBillCustomer(idBill), "Thành công", true));
    }

    @GetMapping("/get-address")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Res> getAddress() {
        return ResponseEntity.ok(new Res(billService.getAddress(), "Thành công", true));
    }
}
