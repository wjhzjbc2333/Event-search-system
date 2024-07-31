package com.example.springbootvue.controller;

import com.example.springbootvue.common.Result;
import com.example.springbootvue.entity.EventTheme;
import com.example.springbootvue.service.EventThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/event-theme")
public class EventThemeController {
    @Autowired
    EventThemeService eventThemeService;

    @PostMapping("/add")
    public Result add(@RequestBody EventTheme eventTheme){
        eventThemeService.addTheme(eventTheme);
        return Result.success(eventTheme);
    }

    @GetMapping("/all")
    public Result selectAll() {
        List<EventTheme> all = eventThemeService.selectAll();
        return Result.success(all);
    }
}
