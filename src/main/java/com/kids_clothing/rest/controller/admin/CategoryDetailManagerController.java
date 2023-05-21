package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.model.request.CategoryDetailResquest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.service.service.CategoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/Manager/CategoryDetailManagerController")
public class CategoryDetailManagerController {
    @Autowired
    CategoryDetailService categoryDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllListCategory() {
        try {
            return ResponseEntity.ok(new Res(categoryDetailService.findAllByIsDeleteFalse(), "Danh sách categoyDetail", true));
        } catch (Exception e) {
            return ResponseEntity.ok(new Res("Loi", false));
        }
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDetailResquest categoryDetailRequest) {
        try {
            Categorydetail categoryDetai = objectMapper.convertValue(categoryDetailRequest, Categorydetail.class);
            return ResponseEntity.ok(new Res(categoryDetailService.saveAll((List<Categorydetail>) categoryDetai), "Thêm thành công", true));

        } catch (Exception e) {
            return ResponseEntity.ok(new Res("Thêm không thành công", false));
        }
    }

    @GetMapping(value = "findById/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getIdCategoryDetail(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(new Res(categoryDetailService.listCategoryDetailById(id), "Sản Phẩm tìm kiếm", true));
        } catch (Exception e) {
            return ResponseEntity.ok(new Res("Không thấy sản phẩm,Sản phẩm không tồn tại", false));
        }
    }
    @GetMapping(value = "findByDropdown")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findByDropdown(@RequestParam("name") String name) {
        System.out.println(name);
        try {
            return ResponseEntity.ok(new Res(categoryDetailService.findByDropdown(name), "Sản Phẩm tìm kiếm", true));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(new Res("Không thấy sản phẩm,Sản phẩm không tồn tại", false));
        }
    }
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryDetailResquest categoryDetailRequest) {
        Optional<Categorydetail> category = categoryDetailService.findById(id);
        if (category.get().getIsDelete() == true) {
            return ResponseEntity.ok(new Res("Sản phẩm không tồn tại", false));
        }
        Categorydetail ct = objectMapper.convertValue(category, Categorydetail.class);
        ct.setId(id);
        Categorydetail cate = categoryDetailService.saveAndFlush(ct);
        return ResponseEntity.ok(new Res(cate, "Cập nhật thành công", true));
    }

    @PostMapping(value = "delete/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> delete(@PathVariable("id") Long id, Categorydetail category) {
        category = categoryDetailService.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Danh mục không tồn tại");
                });
        if (category != null) {
            category.setIsDelete(true);
        }
        return ResponseEntity.ok(new Res(categoryDetailService.saveAndFlush(category), "Xóa thành công", true));

    }

    @PostMapping("/updateInline")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems) throws IOException {
        try {
            ObjectMapper json = new ObjectMapper();
            List<Categorydetail> created = new ArrayList<>();
            List<Categorydetail> updated = new ArrayList<>();
            List<Categorydetail> deleted = new ArrayList<>();

            created = Arrays.asList(json.readValue(createdItems, Categorydetail[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Categorydetail[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Categorydetail[].class));

            if (created.size() > 0) {
                for (Categorydetail entity : created) {
                    entity.setIsDelete(false);
                }
                categoryDetailService.saveAll(created);
            }
            if (updated.size() > 0) {
                for (Categorydetail entity : updated) {
                    entity.setIsDelete(false);
                }
                categoryDetailService.saveAll(updated);
            }
            if (deleted.size() > 0) {
                for (Categorydetail entity : deleted) {
                    entity.setIsDelete(true);
                }
                categoryDetailService.saveAll(deleted);
//				categoryDetailService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(categoryDetailService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }
}
