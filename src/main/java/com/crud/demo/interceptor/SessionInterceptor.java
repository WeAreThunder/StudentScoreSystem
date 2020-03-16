package com.crud.demo.interceptor;

import com.crud.demo.dto.UserDTO;
import com.crud.demo.entity.FormPower;
import com.crud.demo.mapper.FormPowerMapper;
import com.crud.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    //ctrl+o可以创建接口的方法

    @Autowired
    private UsersService usersService;

    @Autowired
    private FormPowerMapper formPowerMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //当登录验证成功后，给cookie加入值
        Cookie[] cookies = request.getCookies();
        if (cookies !=null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("name")){
                    String cookieName = cookie.getValue();
                    UserDTO userDTO = usersService.getUserDTOByName(cookieName);
                    if(userDTO !=null){
                        //在cookie中发现已登录的账号，验证成功后，给session中加入user
                        request.getSession().setAttribute("userDTO",userDTO);
                    }
                    break;
                }
            }
        }
        //得到页面左边菜单的set值，将其赋值给一个char数组，并传回前端
        FormPower formPowerByForm = formPowerMapper.getFormPowerByForm("left");
        char[] leftFormPower = formPowerByForm.getSet().toCharArray();
        request.getSession().setAttribute("LeftFormPower",leftFormPower);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
