package com.example.springbootvue.service;

import com.example.springbootvue.entity.Event;
import com.example.springbootvue.mapper.ESEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

@Service
public class ESEventService {

    @Autowired
    ESEventMapper esEventMapper;

    public void insertEvent(Event event) {
        esEventMapper.save(event);
    }

    public void deleteEvent(String id) {
        esEventMapper.deleteById(id);
    }

    public void updateEvent(Event event) {
        esEventMapper.save(event);
    }

    public void insertEvents(List<Event> events) {
        for(Event event:events){
            esEventMapper.save(event);
        }
    }

    public Optional<Event> findEventById(String id) {
        Optional<Event> byId = esEventMapper.findById(id);
        return byId;
    }

    public List<Event> findEventByTitle(String title) {
        List<Event> events = esEventMapper.findByTitle(title);
        return events;
    }

    public List<Event> findEventByDescription(String des) {
        List<Event> events = esEventMapper.findByDescription(des);
        return events;
    }

    public List<Event> findEventByThemeId(String theme) {
        List<Event> events = esEventMapper.findByThemeId(theme);
        return events;
    }

    public Page<Event> findEventByDescriptionPageable(String des, Pageable page){
        Page<Event> res = esEventMapper.findByDescription(des, page);
        return res;
    }

    public Page<Event> findEventByDescriptionAndEventTypeAndEventThemePageable(String des, String eventType, String eventTheme, Pageable page){
        Page<Event> res = esEventMapper.findByDescriptionAndTypeNameAndThemeName(des, eventType, eventTheme, page);
        return res;
    }

    public Page<Event> findEventByDescriptionAndEventThemePageable(String des, String eventTheme, Pageable page){
        Page<Event> res = esEventMapper.findByDescriptionAndThemeName(des, eventTheme, page);
        return res;
    }

    public Page<Event> findEventByDescriptionAndEventTypePageable(String des, String eventType, Pageable page){
        Page<Event> res = esEventMapper.findByDescriptionAndTypeName(des, eventType, page);
        return res;
    }

    public Page<Event> findAllPageable(Pageable page){
        Page<Event> res = esEventMapper.findAll(page);
        return res;
    }

    public Page<Event> findImageNotEmpty(Pageable page) {
        Page<Event> res = esEventMapper.findByImageNotEmpty(page);
        return res;
    }
}
