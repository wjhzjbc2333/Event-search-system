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
@Document(indexName = "event")
public class Event {
    //事件id
    @Id
    private String id;
    //标题或摘要
    private String title;
    //事件详情、描述
    private String description;
    //事件论元,时间地点等
    private List<String> arguments;
    //发生时间
    private String time;
    //缩略图名称
    private String image;
    //事件类型，每个类型对应一个索引？（try）
    private String typeId;
    private String typeName;
    //专题
    private String themeId;
    private String themeName;

}
