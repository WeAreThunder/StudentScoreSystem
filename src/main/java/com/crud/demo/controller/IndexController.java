package com.crud.demo.controller;

import com.crud.demo.dto.UserDTO;
import com.crud.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public String Index(
                        HttpServletRequest request,
                        HttpServletResponse response){

        return "index";
    }

}
