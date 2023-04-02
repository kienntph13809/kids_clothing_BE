package com.kids_clothing.repository;

import com.kids_clothing.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventDao extends JpaRepository<Event, Long> {
    List<Event> findAllByIsDeleteFalse();

    Optional<Event> findByIdAndIsDeleteFalse(Long id);
}
