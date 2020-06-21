package com.example.demo.service;

import com.example.demo.bean.Result;
import com.example.demo.bean.User;

public interface UserService {
    public Result register(User user);
    public Result login(User user);
}
