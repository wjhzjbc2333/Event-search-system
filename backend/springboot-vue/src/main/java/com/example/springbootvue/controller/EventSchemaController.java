package com.example.springbootvue.controller;


import com.example.springbootvue.common.Result;
import com.example.springbootvue.entity.EventSchema;
import com.example.springbootvue.mapper.EventSchemaMapper;
import com.example.springbootvue.service.EventSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/event-schema")
public class EventSchemaController {

    @Autowired
    EventSchemaService eventSchemaService;

    @PostMapping("/add")
    public Result add(@RequestBody EventSchema eventSchema){
        eventSchemaService.addSchema(eventSchema);
        return Result.success(eventSchema);
    }


    @GetMapping("/all")
    public Result selectAll(){
        List<EventSchema> all = eventSchemaService.selectAll();
        return Result.success(all);
    }

    @GetMapping("findByTypeName")
    public Result selectByTypeName(String typeName) {
        List<EventSchema> all = eventSchemaService.selectByTypeName(typeName);
        return Result.success(all);
    }

}
