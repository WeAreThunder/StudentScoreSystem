package com.crud.demo.controller;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.UserType;
import com.crud.demo.entity.Users;
import com.crud.demo.service.UserTypeService;
import com.crud.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UserTypeService userTypeService;

    @GetMapping("/usersList")
    public String usersList(Model model){
        List<Users> usersList = usersService.getAllUsers();
        model.addAttribute("users",usersList);
        return "usersList";
    }
    @GetMapping("/usersAdd")
    public String getUsersAdd(Model model){
        model.addAttribute("users",new Users());
        //得到对象列表
        List<UserType> userTypeList = userTypeService.userTypeList();
        model.addAttribute(userTypeList);
        return "usersAdd";
    }
    @PostMapping("/usersAdd")
    public String postUsersAdd(@ModelAttribute Users users){
        users.setCraterTime(System.currentTimeMillis());
        usersService.createUsers(users);
        return "redirect:/usersList";
    }
    //更新用户信息
    @GetMapping("/usersUpdate/users/{name}")
    public String getUsersUpdate(@PathVariable("name") String name,
                                   Model model){
        Users users = usersService.getUsers(name);
        model.addAttribute("users",users);
        //得到对象列表
        List<UserType> userTypeList = userTypeService.userTypeList();
        model.addAttribute(userTypeList);
        return "usersUpdate";
    }
    //更新后返回
    @PostMapping("/usersUpdate")
    public String usersUpdate(@ModelAttribute Users users){
        users.setCraterTime(System.currentTimeMillis());
        usersService.updateUsersByName(users);
        return "redirect:/usersList";
    }
    //删除用户信息
    @GetMapping("/usersDel/{name}")
    public String delUsersByName(@PathVariable("name") String name){
        usersService.delUsersByName(name);
        return "redirect:/usersList";
    }
    //模糊查询用户信息
    @GetMapping("/users/searchByName")
    public String getStudentsByNumber(@RequestParam("name") String name,
                                      Model model){
        List<Users> usersListByName = usersService.getUsersListByName(name);
        System.out.println(usersListByName.toString());
        model.addAttribute("users",usersListByName);
        return "usersList";
    }
}
