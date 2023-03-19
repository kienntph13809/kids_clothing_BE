package com.kids_clothing.dao;

import com.kids_clothing.entity.Typesize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TyperSizeDao extends JpaRepository<Typesize, Long> {
    List<Typesize> findAll();
    
    List<Typesize> findAllByIsDeleteFalse();

    Optional<Typesize> findById(Long id);

    Optional<Typesize> findByName(String name);

}
