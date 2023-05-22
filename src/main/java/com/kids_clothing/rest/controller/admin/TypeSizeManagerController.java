package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.entity.Typesize;
import com.kids_clothing.model.request.TypeSizeRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.TypeSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Manager/TypeSizeManagerController")
public class TypeSizeManagerController {
    @Autowired
    TypeSizeService typeSizeService;

    @GetMapping("/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new Res(typeSizeService.findAllByIsDeleteFalse(), "OK", true));
    }

    @PostMapping("/updateInline")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems) {
        try {
            ObjectMapper json = new ObjectMapper();
            List<Typesize> created;
            List<Typesize> updated;
            List<Typesize> deleted;

            created = Arrays.asList(json.readValue(createdItems, Typesize[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Typesize[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Typesize[].class));

            if (created.size() > 0) {
                for (Typesize entity : created) {
                    entity.setIsDelete(false);
                }
                typeSizeService.saveAll(created);
            }
            if (updated.size() > 0) {
                for (Typesize entity : updated) {
                    entity.setIsDelete(false);
                }
                typeSizeService.saveAll(updated);
            }
            if (deleted.size() > 0) {
                for (Typesize entity : deleted) {
                    entity.setIsDelete(true);
                }
                typeSizeService.saveAll(deleted);
//				typeSizeService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(typeSizeService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }

    //code chien
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> createTypeSize(@RequestBody TypeSizeRequest typeSizeRequest) {
        return ResponseEntity.ok(new Res(typeSizeService.create(typeSizeRequest), "Thêm thành công", true));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> updateTypeSize(@PathVariable("id") Long id, @RequestBody TypeSizeRequest typeSizeRequest) {
        return ResponseEntity.ok(new Res(typeSizeService.update(id, typeSizeRequest), "Thêm thành công", true));
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> deleteTypeSize(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Res(typeSizeService.delete(id), "Thêm thành công", true));
    }

    @GetMapping("/findby_name/{name}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> findbyname(@PathVariable String name) {
        return ResponseEntity.ok(new Res(typeSizeService.finbylikename(name), "thành công", true));
    }
}
