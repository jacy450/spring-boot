package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @RequestMapping({"/", "login.html"})
    public String index() {
        return "login";
    }

    @PostMapping("/user/login")
    public String loginAdmin(@RequestParam("Username") String username, @RequestParam("Password") String password, ModelMap modelMap, HttpSession httpSession) {
        if ("admin".equals(username) && "123456".equals(password)) {
            httpSession.setAttribute("user", username);
            return "dashboard";
        } else {
            modelMap.addAttribute("msg", "账号或者密码出错");
            return "login";
        }
    }
}
