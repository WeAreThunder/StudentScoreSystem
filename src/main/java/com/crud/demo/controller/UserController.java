package com.crud.demo.controller;

import com.crud.demo.entity.User;
import com.crud.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /*查询用户列表*/
    @RequestMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.userListByNew());
        return "list";
    }

    /*删除用户*/
    @RequestMapping("/del")
    public String deleteUser(Integer id) {
        userService.delete(id);
        return "redirect:/list";
    }

    /*添加用户页面*/
    @RequestMapping("/add")
    public String addUser(ModelMap map) {
        map.addAttribute("user", new User());
        return "add";
    }

    /*更新用户页面*/
    @RequestMapping("/update")
    public String updateUser(ModelMap map) {
        map.addAttribute("user", new User());

        return "update";
    }

    @GetMapping("/update/user/{id}")
    public String updateUserbyId(@PathVariable("id") Integer id,
                                 Model model){
        User user = userService.getById(id);
        model.addAttribute("user",user);
        return "update";
    }

    /*添加完用户后重定向到list页面*/
    @RequestMapping("/saveI")
    public String saveI(@ModelAttribute User user) {
        userService.insert(user);
        return "redirect:/list";
    }

    /*更新完用户后重定向到list页面*/
    @RequestMapping("/saveU")
    public String saveU(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/list";
    }
}
