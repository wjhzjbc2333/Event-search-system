package com.example.springbootvue.mapper;

import com.example.springbootvue.entity.Event;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface EventMapper {
    @Insert("insert into `event` (eventName, time, country, type, detail) " +
            "values (#{eventName}, #{time}, #{country}, #{type}, #{detail}) ")
    void Insert(Event event);
}
