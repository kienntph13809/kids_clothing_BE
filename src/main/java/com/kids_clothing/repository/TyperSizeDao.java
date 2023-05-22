package com.kids_clothing.repository;

import com.kids_clothing.entity.Typesize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TyperSizeDao extends JpaRepository<Typesize, Long> {
    List<Typesize> findAll();

    List<Typesize> findAllByIsDeleteFalse();

    Optional<Typesize> findById(Long id);

    Optional<Typesize> findByName(String name);

    @Query("SELECT T FROM Typesize T WHERE T.name LIKE %:name%")
    List<Typesize> findByNameLike(@Param("name") String name);

}
