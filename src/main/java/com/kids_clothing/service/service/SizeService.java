package com.kids_clothing.service.service;

import com.kids_clothing.entity.Size;
import com.kids_clothing.entity.Typesize;
import com.kids_clothing.model.request.SizeRequest;

import java.util.List;

public interface SizeService {
    List<Size> findAll();

    List<Size> findByTypeSize(Typesize typeSize);
    
    List<Size> findAllByIsDeleteFalse();

    List<Size> saveAll(List<Size> size);

    void deleteInBatch(List<Size> size);

    Size create(SizeRequest sizeRequest);

    Size update(Long idsize, SizeRequest sizeRequest);

    Size delete(Long idsize);
}
