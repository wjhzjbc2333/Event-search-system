package com.example.springbootvue.service;

import com.example.springbootvue.entity.Event;
import com.example.springbootvue.mapper.ESEventMapper;
import com.example.springbootvue.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    ESEventMapper eventMapper;

    public void insertEvent(Event event) {

        eventMapper.save(event);
    }

    public void updateEvent(Event event) {

        eventMapper.save(event);
    }

    public void deleteEventById(String id) {

        eventMapper.deleteById(id);

    }

    public Optional<Event> findEventByID(String id) {
        Optional<Event> byId = eventMapper.findById(id);
        return byId;
    }




}
