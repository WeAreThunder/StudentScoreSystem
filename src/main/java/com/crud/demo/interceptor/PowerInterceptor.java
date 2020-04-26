package com.crud.demo.interceptor;

import com.crud.demo.dto.UserDTO;
import com.crud.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class PowerInterceptor implements HandlerInterceptor {

    @Autowired
    private UsersService usersService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int power = 0;

        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        List<String> urlLevelList = new ArrayList<>();

        urlLevelList.add("studentsInfo");
        urlLevelList.add("studentScore");
        urlLevelList.add("student");
        urlLevelList.add("课程管理面板");
        urlLevelList.add("course");
        urlLevelList.add("teachersInfo");
        urlLevelList.add("teacher");
        urlLevelList.add("班级管理面板");
        urlLevelList.add("classList");
        urlLevelList.add("管理员管理面板");
        urlLevelList.add("users");
        urlLevelList.add("userType");
        urlLevelList.add("formPower");

        //判断访问地址需要的权限
        for (String urlLevel : urlLevelList) {
            if (requestURI.indexOf(urlLevel) != -1){
                int index = urlLevelList.indexOf(urlLevel);
                System.out.println("用户访问地址为："+servletPath+",在"+urlLevel+"中，所需权限等级为"+index);
                power = index;
                break;
            }
        }

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")){
                String cookieName = cookie.getValue();
                UserDTO userDTO = usersService.getUserDTOByName(cookieName);
                if(userDTO !=null){
                    if (userDTO.getPower() < power){
                        System.out.println("用户"+userDTO.getUsers().getName()+"权限不足！不许偷看，你的权限不足(╬▔皿▔)凸");
                        return false;
                    } else {
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
