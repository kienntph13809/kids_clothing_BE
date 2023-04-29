package com.kids_clothing.repository;

import com.kids_clothing.entity.Event;
import com.kids_clothing.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventDao extends JpaRepository<Event, Long> {
    List<Event> findAllByIsDeleteFalse();

    Optional<Event> findByIdAndIsDeleteFalse(Long id);


    @Query("SELECT E FROM Event E WHERE E.name LIKE %:name%")
    List<Event> findByNameLike(@Param("name") String name);
    @Query("SELECT e FROM Event e ORDER BY e.startday DESC")
    List<Event> findByDayNewCreate();
}
