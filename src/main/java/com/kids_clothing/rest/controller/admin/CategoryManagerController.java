package com.kids_clothing.rest.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.entity.Category;
import com.kids_clothing.entity.Categorydetail;
import com.kids_clothing.request.dto.CategoryRequest;
import com.kids_clothing.response.dto.Res;
import com.kids_clothing.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/Manager/CategoryManagerController")
public class CategoryManagerController {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody Categorydetail categoryRequest) {
        try {
            Category category = objectMapper.convertValue(categoryRequest, Category.class);
            return ResponseEntity.ok(new Res(categoryService.saveAll((List<Category>) category), "Thêm thành công", true));

        } catch (Exception e) {
            return ResponseEntity.ok(new Res("Thêm không thành công", false));
        }
    }

    @GetMapping(value = "/findAllByIsDeleteFalse")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllListCategory() {
        try {
            return ResponseEntity.ok(new Res(categoryService.findAllByIsDeleteFalse(), "Danh sách categoy", true));
        } catch (Exception e) {
            return ResponseEntity.ok(new Res("Loi", false));
        }
    }

    @GetMapping(value = "findById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getIdCategory(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(new Res(categoryService.listCategoryById(id), "Sản Phẩm tìm kiếm", true));
        } catch (Exception e) {
            return ResponseEntity.ok(new Res("Không thấy sản phẩm,Sản phẩm không tồn tại", false));
        }
    }

    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryRequest categoryRequest) {
        Optional<Category> category = categoryService.findById(id);
        if (category.get().getIsDelete() == true) {
            return ResponseEntity.ok(new Res("Sản phẩm không tồn tại", false));
        }
        Category ct = objectMapper.convertValue(category, Category.class);
        ct.setId(id);
        Category cate = categoryService.save(ct);
        return ResponseEntity.ok(new Res(cate, "Cập nhật thành công", true));
    }

    @PostMapping(value = "delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Res> delete(@PathVariable("id") Long id, Category category) {
        category = categoryService.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Danh mục không tồn tại");
                });
        if (category != null) {
            category.setIsDelete(true);
        }
        return ResponseEntity.ok(new Res(categoryService.save(category), "Xóa thành công", true));

    }

    @PostMapping("/updateInline")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateInline(@RequestParam(required = false, value = "createdItems") String createdItems,
                                          @RequestParam(required = false, value = "updatedItems") String updatedItems,
                                          @RequestParam(required = false, value = "deletedItems") String deletedItems) throws IOException {
        try {
            ObjectMapper json = new ObjectMapper();
            List<Category> created = new ArrayList<>();
            List<Category> updated = new ArrayList<>();
            List<Category> deleted = new ArrayList<>();

            created = Arrays.asList(json.readValue(createdItems, Category[].class));
            updated = Arrays.asList(json.readValue(updatedItems, Category[].class));
            deleted = Arrays.asList(json.readValue(deletedItems, Category[].class));

            if (created.size() > 0) {
                for (Category entity : created) {
                    entity.setIsDelete(false);
                }
                categoryService.saveAll(created);
            }
            if (updated.size() > 0) {
                for (Category entity : updated) {
                    entity.setIsDelete(false);
                }
                categoryService.saveAll(updated);
            }
            if (deleted.size() > 0) {
                for (Category entity : deleted) {
                    entity.setIsDelete(true);
                }
                categoryService.saveAll(deleted);
//				categoryService.deleteInBatch(deleted);
            }
            return ResponseEntity.ok(new Res(categoryService.findAllByIsDeleteFalse(), "Save success", true));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.ok(new Res("Save failed", false));
        }
    }
}
