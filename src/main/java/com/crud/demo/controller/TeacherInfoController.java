package com.crud.demo.controller;

import com.crud.demo.dto.TeacherDTO;
import com.crud.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teachersInfo")
public class TeacherInfoController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("")
    public String teachersInfo(Model model){
        List<TeacherDTO> teacherDTOList = teacherService.getTeacherDTOList();
        model.addAttribute("teacherDTOList",teacherDTOList);
        System.out.println(teacherDTOList.toString());
        return "teachersInfo";
    }
}
