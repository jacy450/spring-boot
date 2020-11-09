package com.example.demo.resolver;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //设立变量来接收按钮返回的值，返回的值的类型为xx_xx
        String lan = httpServletRequest.getParameter("lan");
        Locale locale = Locale.getDefault();//获取系统默认的locale值
        if (!StringUtils.isEmpty(lan)) {  //假如lan不为空，即按了按钮
            //进行截串，以"_"为区分
            String[] split = lan.split("_");
            //将截串后的两个字段放入locale中
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
