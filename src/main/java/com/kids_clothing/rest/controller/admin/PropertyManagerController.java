package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.entity.Property;
import com.kids_clothing.response.dto.Res;
import com.kids_clothing.service.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Manager/PropertyManagerController")
public class PropertyManagerController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        List<Property> entity = propertyService.findAllByIsDeleteFalse();
        if (entity.size() > 0)
            return ResponseEntity.ok(new Res(propertyService.findAllByIsDeleteFalse(), "OK", true));
        else
            return ResponseEntity.ok(new Res("Not OK", false));
    }

    @PostMapping("/updateInline")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems) throws IOException {
        try {
            ObjectMapper json = new ObjectMapper();
            List<Property> created = new ArrayList<>();
            List<Property> updated = new ArrayList<>();
            List<Property> deleted = new ArrayList<>();

            created = Arrays.asList(json.readValue(createdItems, Property[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Property[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Property[].class));

            if (created.size() > 0) {
                for (Property entity : created) {
                    entity.setIsDelete(false);
                }
                propertyService.saveAll(created);
            }
            if (updated.size() > 0) {
                for (Property entity : updated) {
                    entity.setIsDelete(false);
                }
                propertyService.saveAll(updated);
            }
            if (deleted.size() > 0) {
                for (Property entity : deleted) {
                    entity.setIsDelete(true);
                }
                propertyService.saveAll(deleted);
//				propertyService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(propertyService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }
}
