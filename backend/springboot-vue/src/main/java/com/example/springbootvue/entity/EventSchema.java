package com.example.springbootvue.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
@Document(indexName = "event-schema")
public class EventSchema {
    //事件类型id
    @Id
    private String id;

    //事件类型名称
    private String typeName;

    //触发词
    private List<String> trigger;

    //与此事件类型对应的论元(时间、地点等)
    private List<String> arguments;

}
