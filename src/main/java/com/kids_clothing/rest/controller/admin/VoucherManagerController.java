package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.entity.Voucher;
import com.kids_clothing.model.request.VoucherRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/Manager/VoucherManagerController")
@CrossOrigin("http://localhost:4200/")

public class VoucherManagerController {

    @Autowired
    VoucherService voucherService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> cteateEvent(@RequestBody VoucherRequest voucherRequest) throws ParseException {
        return ResponseEntity.ok(new Res(voucherService.create(voucherRequest), "Thêm thành công", true));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> updateEvent(@PathVariable("id") Long id, @RequestBody VoucherRequest voucherRequest) throws ParseException {
        return ResponseEntity.ok(new Res(voucherService.update(id, voucherRequest), "Thêm thành công", true));
    }

    @GetMapping("/detele/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> deteleEvent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Res(voucherService.detele(id), "Xóa thành công", true));
    }

    @GetMapping("/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findAll() {
        return ResponseEntity.ok(new Res(voucherService.findAllByIsDeleteFalse(), "thành công", true));
    }

    @GetMapping("/findAll/{idEvent}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findAll(@PathVariable("idEvent") Long idEvent) {
        return ResponseEntity.ok(new Res(voucherService.findAllVoucherByIdEvent(idEvent), "thành công", true));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Res(voucherService.findById(id), "thành công", true));
    }

    //
    @PostMapping("/updateInline")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems) {
        try {
            ObjectMapper json = new ObjectMapper();
            List<Voucher> created;
            List<Voucher> updated;
            List<Voucher> deleted;

            created = Arrays.asList(json.readValue(createdItems, Voucher[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Voucher[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Voucher[].class));

            if (created.size() > 0) {
                for (Voucher entity : created) {
                    entity.setIsDelete(false);
                }
                voucherService.saveAll(created);
            }
            if (updated.size() > 0) {
                for (Voucher entity : updated) {
                    entity.setIsDelete(false);
                }
                voucherService.saveAll(updated);
            }
            if (deleted.size() > 0) {
                for (Voucher entity : deleted) {
                    entity.setIsDelete(true);
                }
                voucherService.saveAll(deleted);
//				categoryDetailService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(voucherService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }
}
