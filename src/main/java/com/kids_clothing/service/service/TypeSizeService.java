package com.kids_clothing.service.service;

import com.kids_clothing.entity.Typesize;
import com.kids_clothing.model.request.TypeSizeRequest;

import java.util.List;
import java.util.Optional;

public interface TypeSizeService {
    List<Typesize> findAll();

    List<Typesize> findAllByIsDeleteFalse();

    Optional<Typesize> findById(Long id);

    List<Typesize> saveAll(List<Typesize> typeSize);

    void deleteInBatch(List<Typesize> typeSize);

    Typesize create(TypeSizeRequest typeSizeRequest);

    Typesize update(Long id, TypeSizeRequest typeSizeRequest);

    Typesize delete(Long id);

    List<Typesize> finbylikename(String name);
}
