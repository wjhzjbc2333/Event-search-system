package com.example.springbootvue.service;

import com.example.springbootvue.entity.User;
import com.example.springbootvue.exception.ServiceException;
import com.example.springbootvue.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertUser(User user){
        userMapper.Insert(user);
    }

    public User login(User user){
        User tmpUser = userMapper.selectByUsername(user.getUsername());
        if(tmpUser == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if(!user.getPassword().equals(tmpUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        return tmpUser;
    }
}
