package com.example.springbootvue.mapper;

import com.example.springbootvue.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserMapper {
    @Insert("insert into `user` (username, phone, email, address, password) " +
            "values (#{username}, #{phone}, #{email}, #{address}, #{password}) ")
    void Insert(User user);

    @Select("select * from user where username = #{username} order by id desc")
    User selectByUsername(String username);
}
