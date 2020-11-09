## Springboot中给Interceptor配置的excludePathPatterns无效

> Interceptor的配置如下，如果发现用户没有登录就重定向到主页面"index"
```java
public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 根据sessionId检查是否登录
        if(!LoginConst.sessionIds.contains(session.getId())) {
            response.sendRedirect(request.getContextPath() + "/index");
            return false;
        }
        return true;
    }

    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws Exception {

    }
}
```
>在这里对"/index"进行配置，设置为不拦截，即当访问的url为"http://localhost:9090/index"时不进行拦截
```java
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/index");
    }
}
```

```java
@Controller
public class LoginController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
```
> 发现会报重定向次数过多的问题
```text
经过一步一步的排查分析，发现是Controller层的代码有问题
当接收"/index"的时候返回的view是"index"
由于没有使用@ResponseBody，所以这个字符串会被解析为一个视图而不是JSON串
所以当在Interceptor中进行redirect重定向之后
再一次进行重定向的路径为http://localhost:8080/index.html
此时的相对路径为"/index.html"，不再被排除的路径内，所以又会被拦截
```

> 此处改为
```java
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/index");
        registration.excludePathPatterns("/index.html");
    }
}
```

