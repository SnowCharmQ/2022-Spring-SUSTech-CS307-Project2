package com.cs307.project.config;

import com.cs307.project.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/img/**");
        patterns.add("/fonts/**");
        patterns.add("/js/**");
        patterns.add("/web/db-login.html");
        patterns.add("/web/db-reg.html");
        patterns.add("/users/db-login");
        patterns.add("/users/db-reg");
        patterns.add("/api/select-model");
        patterns.add("/api/insert-model");
        patterns.add("/api/delete-model");
        patterns.add("/api/update-model");
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
