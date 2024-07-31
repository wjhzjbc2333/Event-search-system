package com.example.springbootvue.mapper;

import com.example.springbootvue.entity.EventTheme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EventThemeMapper extends ElasticsearchRepository<EventTheme, String> {
    List<EventTheme> findByThemeName(String themeName);
}
