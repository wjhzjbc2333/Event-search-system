package com.example.springbootvue.service;

import com.example.springbootvue.entity.Event;
import com.example.springbootvue.entity.EventSchema;
import com.example.springbootvue.mapper.EventSchemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventSchemaService {
    @Autowired
    EventSchemaMapper eventSchemaMapper;

    public void addSchema(EventSchema eventSchema) {
        eventSchemaMapper.save(eventSchema);
    }

    public List<EventSchema> selectAll(){
        Iterable<EventSchema> all = eventSchemaMapper.findAll();
        List<EventSchema> res = new ArrayList<EventSchema>();
        all.forEach(single ->{res.add(single);});
        return res;
    }

    public List<EventSchema> selectByTypeName(String typeName){
        List<EventSchema> all = eventSchemaMapper.findByTypeNameEquals(typeName);
        return all;
    }

}
