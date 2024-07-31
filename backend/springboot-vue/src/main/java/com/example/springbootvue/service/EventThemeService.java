package com.example.springbootvue.service;

import com.example.springbootvue.entity.Event;
import com.example.springbootvue.entity.EventTheme;
import com.example.springbootvue.mapper.EventThemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventThemeService {
    @Autowired
    EventThemeMapper eventThemeMapper;

    public void addTheme(EventTheme eventTheme){
        eventThemeMapper.save(eventTheme);
    }

    public List<EventTheme> selectAll() {
        Iterable<EventTheme> all = eventThemeMapper.findAll();
        List<EventTheme> res = new ArrayList<EventTheme>();
        all.forEach(single -> {res.add(single);});
        return res;
    }

    public List<EventTheme> selectByThemeName(String themeName) {
        List<EventTheme> all = eventThemeMapper.findByThemeName(themeName);
        return all;
    }
}
