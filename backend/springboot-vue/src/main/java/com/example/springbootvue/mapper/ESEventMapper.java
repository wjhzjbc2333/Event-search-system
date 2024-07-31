package com.example.springbootvue.mapper;

import com.example.springbootvue.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ESEventMapper extends ElasticsearchRepository<Event, String> {

    List<Event> findByTitle(String title);

    List<Event> findByDescription(String des);

    List<Event> findByThemeId(String themeId);

    Page<Event> findByDescription(String des, Pageable page);

    Page<Event> findByDescriptionAndTypeNameAndThemeName(String des, String typeName, String themeName, Pageable page);

    Page<Event> findByDescriptionAndTypeName(String des, String typeName, Pageable page);

    Page<Event> findByDescriptionAndThemeName(String des, String themeName, Pageable page);

    Page<Event> findByImageNotEmpty(Pageable page);

}
