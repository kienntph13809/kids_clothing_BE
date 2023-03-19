package com.kids_clothing.dao;

import com.kids_clothing.entity.Size;
import com.kids_clothing.entity.Typesize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SizeDao extends JpaRepository<Size, Long> {
    List<Size> findAll();

    List<Size> findByTypesize(Typesize typesize);
    
    List<Size> findAllByIsDeleteFalse();

    void delete(Size size);

    Optional<Size> findByNameAndAndIdtypesize(String name, Long idtypesite);

    Optional<Size> findByName(String name);
}
