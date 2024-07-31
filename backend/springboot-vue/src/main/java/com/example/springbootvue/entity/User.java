package com.example.springbootvue.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String username;
    private String phone;
    private String email;
    private String address;
    private String password;

}
