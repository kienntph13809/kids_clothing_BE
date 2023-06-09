package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.common.EnumRefund;
import com.kids_clothing.entity.Bill;
import com.kids_clothing.model.request.ShipingRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.repository.BillDao;
import com.kids_clothing.service.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Manager/BillManagerController")
public class BillManagerControler {

    @Autowired
    BillDao billDao;

    @Autowired
    BillService billService;

    //lấy tất cả đơn hàng
    @GetMapping("/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllBill() {
        return ResponseEntity.ok(new Res(billDao.findAll(), "Thành công", true));
    }

    @GetMapping("/cancel/{idbill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> cancelBill(@PathVariable("idbill") String idbill) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(new Res(billService.cancelBillManager(idbill), "Save success", true));
    }

    @GetMapping("/confirm/{idbill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> confirmBill(@PathVariable("idbill") String idbill) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(new Res(billService.confirmBillManager(idbill), "Save success", true));
    }

    @GetMapping("/ship/{idbill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> shipBill(@PathVariable("idbill") String idbill) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(new Res(billService.shipBillManager(idbill), "Save success", true));
    }

    @GetMapping("/received/{idbill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> receivedBill(@PathVariable("idbill") String idbill) {
        return ResponseEntity.ok(new Res(billService.receivedBillManager(idbill), "Save success", true));
    }

    @GetMapping("/refund/{idbill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> refundBill(@PathVariable("idbill") String idbill,
                                          @RequestParam(value = "status", required = false) EnumRefund status,
                                          @RequestParam(value = "note", required = false) String note) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(new Res(billService.refundBillManager(idbill, status, note), "Save success", true));
    }

    //thông tin đơn hàng bên vận chuyển
    @PostMapping("/shiping")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> shipingBill(@RequestBody ShipingRequest shipingRequest) {
        return ResponseEntity.ok(new Res(billService.shipingBill(shipingRequest), "Save success", true));
    }

    @GetMapping("/shiping/{idBill}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> shipingBill(@PathVariable("idBill") String idBill) throws IOException {
        return ResponseEntity.ok(new Res(billService.getShipingBill(idBill), "Save success", true));
    }

    @PostMapping("/updateInline")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems) {
        try {
            ObjectMapper json = new ObjectMapper();
            List<Bill> created;
            List<Bill> updated;
            List<Bill> deleted;

            created = Arrays.asList(json.readValue(createdItems, Bill[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Bill[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Bill[].class));

            if (created.size() > 0) {
                for (Bill entity : created) {
                    entity.setIsDelete(false);
                }
                billDao.saveAll(created);
            }
            if (updated.size() > 0) {
                for (Bill entity : created) {
                    entity.setIsDelete(false);
                }
                billDao.saveAll(updated);
            }
            if (deleted.size() > 0) {
                for (Bill entity : deleted) {
                    entity.setIsDelete(true);
                }
                billDao.saveAll(deleted);
//				categoryDetailService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(billDao.findAll(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }

    @GetMapping("/findByDayNewCreate")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findByDayNewCreate() {
        List<Bill> entity = billService.findByDayNewCreate();
        return ResponseEntity.ok(new Res(entity, "Success", true));
    }

    @GetMapping("/findby_status")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findstatus(@RequestParam("name") String name) {
        return ResponseEntity.ok(new Res(billService.findBystatus(name), "thành công", true));
    }

    @GetMapping("/findby_statusshiping")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findstatusshiping(@RequestParam("name") String name) {
        return ResponseEntity.ok(new Res(billService.findBystatusshipping(name), "thành công", true));
    }

    @GetMapping("/findby_payment")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findpayment(@RequestParam("name") int name) {
        return ResponseEntity.ok(new Res(billService.findbypayment(name), "thành công", true));
    }

    @GetMapping("/findby_date_and_payment_and_status")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findby_date_and_payment_and_status(@RequestParam("date") String date, @RequestParam("dateto") String dateto, @RequestParam("payment") String paymentStr, @RequestParam("status") String status) {
        Integer payment = null;
        if (paymentStr != null && !paymentStr.isEmpty()) {
            try {
                payment = Integer.parseInt(paymentStr);
            } catch (NumberFormatException e) {
                // Handle invalid format
                return ResponseEntity.badRequest().body(new Res(null, "Invalid payment value", false));
            }
        }
        return ResponseEntity.ok(new Res(billService.findByDateAndpaymentAndstatus(date, dateto, payment, status), "thành công", true));
    }

}
