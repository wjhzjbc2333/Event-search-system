package com.example.springbootvue.mapper;

import com.example.springbootvue.entity.EventSchema;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EventSchemaMapper extends ElasticsearchRepository<EventSchema, String> {
    List<EventSchema> findByTypeNameEquals(String typeName);

}
