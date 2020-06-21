package com.example.demo.controller;

import com.example.demo.bean.Result;
import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public Result register(User user) {
        return userService.register(user);
    }

    @PostMapping(value = "/login")
    public Result login(User user){
        return userService.login(user);
    }
}
