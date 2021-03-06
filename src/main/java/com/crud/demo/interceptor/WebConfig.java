package com.crud.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//下面这个注解加上之后可能会拦截css样式,我在添加了这个注释后发现jQuery被拦截了无法生效
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private PowerInterceptor powerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设定哪些地址需要被拦截，.addPathPatterns("/**")设置哪些地址需要被拦截；.excludePathPatterns("/admin/**")加在后面设定哪些地址不需要被拦截
        //实现持久化登录
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
        //权限拦截

        registry.addInterceptor(powerInterceptor).addPathPatterns("/studentScore**")
                .addPathPatterns("/student**").addPathPatterns("/studentScoreUpdate/studentScore/**")
                .addPathPatterns("/course**")
                .addPathPatterns("/teacher**")
                .addPathPatterns("/class**")
                .addPathPatterns("/users**")
                .addPathPatterns("/userType**")
                .addPathPatterns("/formPower**")
                //不拦截下面这两个地址
                .excludePathPatterns("/")
                .excludePathPatterns("/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置文件上传
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/MyWork/upload/");
    }
}