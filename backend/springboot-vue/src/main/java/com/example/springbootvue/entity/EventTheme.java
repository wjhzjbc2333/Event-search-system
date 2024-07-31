package com.example.springbootvue.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@Data
@Document(indexName = "event-theme")
public class EventTheme {
    @Id
    private String id;

    private String themeName;


}
