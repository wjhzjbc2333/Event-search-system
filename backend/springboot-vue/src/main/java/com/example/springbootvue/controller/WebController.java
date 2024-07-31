package com.example.springbootvue.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springbootvue.common.Result;
import com.example.springbootvue.entity.User;
import com.example.springbootvue.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {

    @Resource
    UserService userService;

    @GetMapping("/")
    public Result hello(String name) {
        return Result.success(name);
    }

    @PostMapping("login")
    public Result login(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("输入有问题");
        }
        user = userService.login(user);
        return Result.success(user);
    }
}
