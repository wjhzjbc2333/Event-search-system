package com.example.springbootvue.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.MultiResolutionImage;

@Getter
@Setter
@NoArgsConstructor
public class Image {

    private String name;
    private Integer size;
    private String uid;
    private MultipartFile raw;
}
