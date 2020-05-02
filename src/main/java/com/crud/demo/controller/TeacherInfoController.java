package com.crud.demo.controller;

import com.crud.demo.dto.TeacherDTO;
import com.crud.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/teachersInfo")
public class TeacherInfoController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("")
    public String teachersInfo(Model model,
                               @RequestParam(name = "job",defaultValue="all")String job){
        List<TeacherDTO> teacherDTOList = teacherService.getTeacherDTOListByJob(job);
        model.addAttribute("teacherDTOList",teacherDTOList);
        model.addAttribute("job",job);
        return "teachersInfo";
    }
}
