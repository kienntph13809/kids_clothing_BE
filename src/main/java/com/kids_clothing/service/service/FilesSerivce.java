package com.kids_clothing.service.service;


import com.kids_clothing.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FilesSerivce {
    void init();


    Resource load(String filename);

    void save(MultipartFile file, Image img);

    Stream<Path> loadAll();

    ResponseEntity get(String filename);

    List<Image> ListImagesByProduct(Long idProduct);

    Image saveAndFlush(Image image);

    Optional<Image> findById(Long id);
}
