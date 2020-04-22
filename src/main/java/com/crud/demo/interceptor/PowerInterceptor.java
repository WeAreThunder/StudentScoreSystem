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

@Service
public class PowerInterceptor implements HandlerInterceptor {

    private int power;

    @Autowired
    private UsersService usersService;

    public void setPower(int power){
        this.power = power;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")){
                String cookieName = cookie.getValue();
                UserDTO userDTO = usersService.getUserDTOByName(cookieName);
                if(userDTO !=null){
                    if (userDTO.getPower() < power){
                        System.out.println("用户"+userDTO.getUsers().getName()+"权限不足！不许偷看，你的权限不足(╬▔皿▔)凸");
                        return false;
                    }
                    else return true;
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
