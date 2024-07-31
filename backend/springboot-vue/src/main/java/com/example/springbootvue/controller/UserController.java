package com.example.springbootvue.controller;

import com.example.springbootvue.common.Result;
import com.example.springbootvue.entity.User;
import com.example.springbootvue.mapper.UserMapper;
import com.example.springbootvue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/all")
    public Result selectAll(@RequestParam String username) {
        User user = userMapper.selectByUsername(username);
        return Result.success(user);
    }

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody User user) {

        userService.insertUser(user);
        return Result.success(user);
    }

}
