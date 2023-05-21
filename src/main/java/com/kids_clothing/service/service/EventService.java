package com.kids_clothing.service.service;

import com.kids_clothing.entity.Event;
import com.kids_clothing.model.request.EventRequest;

import java.text.ParseException;
import java.util.List;

public interface EventService {

    Event create(EventRequest eventRequest) throws ParseException;

    Event update(Long id, EventRequest eventRequest) throws ParseException;

    Event detele(Long id);

    List<Event> findAll();

    List<Event> findAllByIsDeleteFalse();

    Event findById(Long id);

    List<Event> findbyname(String name);

    List<Event> findByDayNewCreate();
}
