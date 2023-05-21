package com.kids_clothing.rest.controller.admin;

import com.kids_clothing.entity.Image;
import com.kids_clothing.entity.Product;
import com.kids_clothing.model.request.FileInfo;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.model.response.ResponeMess;
import com.kids_clothing.service.service.FilesSerivce;
import com.kids_clothing.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin("*")
@RequestMapping("/manager/image")
public class RestUploatImgController {

    @Autowired
    FilesSerivce storageService;

    @Autowired
    ProductService productService;

    //save
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponeMess> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("idPro") String idPro) {
        String message;
        Long idProduct = Long.parseLong(idPro);
        Product pro = productService.findById(idProduct);
        long current = new java.util.Date().getTime();
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.stream(files).forEach(file -> {
                fileNames.add("sp" + current + file.getOriginalFilename());
                Image img = new Image();
                img.setProduct(pro);
                storageService.save(file, img);
            });
            message = "Tải các tệp ảnh thành công:  " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponeMess(message));
        } catch (Exception e) {
            message = "Tải các tệp ảnh không thành công!";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponeMess(message));
        }
    }

    //Hiển thị danh sách sản phẩm theo id
    @GetMapping(value = "/list/{idProduct}")
    public ResponseEntity<?> getImgByProduct(@PathVariable("idProduct") Long idProduct) {
        Product pro = productService.findById(idProduct);
        List<Image> list = storageService.ListImagesByProduct(idProduct);
        return ResponseEntity.ok(new Res(list, "Danh sách ảnh của sản phẩm :  " + pro.getName(), false));
    }

    //Hiện thị ảnh từ mutipath lên trên localhost
    @GetMapping(value = "/get/{fileName}")
    public ResponseEntity get(@PathVariable("fileName") String fileName) {
        return storageService.get(fileName);
    }

    //Xóa ảnh của sản phẩm thep id product
    @PostMapping(value = "/delete/{idImg}")
    public ResponseEntity<Res> delete(@PathVariable("idImg") Long idImg, Image images) {
        images = storageService.findById(idImg).orElseThrow(
                () -> {
                    throw new RuntimeException("Ảnh không tồn tại");
                }
        );
        if (images != null) {
            images.setIsDelete(true);
        } else {
            throw new RuntimeException("Sản phẩm chưa có ảnh này");
        }
        return ResponseEntity.ok(new Res(storageService.saveAndFlush(images), "Xóa thành công", true));
    }

    // lấy thông tin của file ảnh trong mutipath
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(RestUploatImgController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    // lấy thông tin của một file ảnh
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
