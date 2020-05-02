package com.crud.demo.controller;

import com.crud.demo.dto.UserDTO;
import com.crud.demo.entity.Users;
import com.crud.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public String gotoList(@RequestParam("name")String name,
                           @RequestParam("password") String password,
                           Model model,
                           HttpServletRequest request,
                           HttpServletResponse response){
        UserDTO userDTOByName = usersService.getUserDTOByName(name);
        if (userDTOByName != null && password.equals(userDTOByName.getUsers().getPassword())){
            //登录成功后添加cookie
            Cookie cookie = new Cookie("name",name);
            cookie.setMaxAge(60*60*24);//设定cookie寿命为24小时
            response.addCookie(cookie);
            return "redirect:/";
        }else {
            model.addAttribute("error","用户名或密码错误");
            return "login";
        }
    }

    @GetMapping("/login")
    public String gotoLogin(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //登出时删除session和cookie
        request.getSession().removeAttribute("userDTO");
        Cookie cookie = new Cookie("name",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
