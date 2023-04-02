package com.kids_clothing.service.impl;


import com.kids_clothing.repository.ImageDao;
import com.kids_clothing.entity.Image;
import com.kids_clothing.service.service.FilesSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FilesServiceImpl implements FilesSerivce {

    private final Path root = Paths.get("severImg");

    @Autowired
    ImageDao imgDao;;

    @Override
    public void init() {
        try {
            if (Files.notExists(root)) {
                Files.createDirectory(root);
            } else {
                System.out.println("100%");

            }
        } catch (IOException e) {
            throw new RuntimeException("Không thể khởi tạo thư mục để tải ảnh lên!");
        }
    }
    //luu anh
    @Override
    public void save(MultipartFile file, Image img) {
        //DateDto current = new DateDto();
        long current = new java.util.Date().getTime();
        try {
            String nameImg = "sp" + current + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(nameImg));
            img.setName(nameImg);
            img.setIsDelete(false);
            img.setUrl("http://localhost:8080/manager/image/get/" + nameImg);
            imgDao.save(img);

        } catch (Exception e) {
            throw new RuntimeException("Không thể lưu trữ tệp. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Không thể đọc tệp!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public ResponseEntity get(String fileName) {
        Path path = root.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                MediaType mediaType = MediaTypeFactory.getMediaType(fileName).get();
                return ResponseEntity
                        .ok()
                        .contentType(mediaType)
                        .body(resource);
            }
        } catch (MalformedURLException e) {
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loi roi");
    }

    @Override
    public List<Image> ListImagesByProduct(Long idProduct) {
        return imgDao.ListImagesByProduct(idProduct);
    }

    @Override
    public Optional<Image> findById(Long id) {
        // TODO Auto-generated method stub
        return imgDao.findById(id);
    }

    @Override
    @Transactional
    public Image saveAndFlush(Image image) {
        // TODO Auto-generated method stub
        return imgDao.saveAndFlush(image);
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Không thể tải các tệp!");
        }
    }
}

