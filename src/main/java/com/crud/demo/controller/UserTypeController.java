package com.crud.demo.controller;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.UserType;
import com.crud.demo.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserTypeController {
    @Autowired
    private UserTypeService userTypeService;

    @GetMapping("/userTypeList")
    public String getUserTypeList(Model model){
        List<UserType> userTypeList = userTypeService.userTypeList();
        model.addAttribute("userTypes",userTypeList);
        return "userTypeList";
    }
    @GetMapping("/userTypeAdd")
    public String getUserTypeAdd(Model model){
        model.addAttribute("userType",new UserType());
        return "userTypeAdd";
    }
    @PostMapping("/userTypeAdd")
    public String postUserTypeAdd(@ModelAttribute UserType userType){
        userTypeService.createUserType(userType);
        return "redirect:/userTypeList";
    }
    //更新用户类型信息
    @GetMapping("/userTypeUpdate/userType/{type}")
    public String getUserTypeUpdate(@PathVariable("type") String type,
                                   Model model){
        UserType userTypeByType = userTypeService.getUserTypeByType(type);
        model.addAttribute("userType",userTypeByType);
        return "userTypeUpdate";
    }
    //更新后返回
    @PostMapping("/userTypeUpdate")
    public String userTypeUpdate(@ModelAttribute UserType userType){
        userTypeService.updateUserTypeByType(userType);
        return "redirect:/userTypeList";
    }
    //删除学生信息
    @GetMapping("/userTypeDel/{type}")
    public String delUserTypeByType(@PathVariable("type") String type){
        userTypeService.delUserTypeByType(type);
        return "redirect:/userTypeList";
    }
}
