package com.crud.demo.controller;

import com.crud.demo.dto.studentDTO;
import com.crud.demo.entity.Class;
import com.crud.demo.service.ClassService;
import com.crud.demo.service.StudentScoreService;
import com.crud.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/studentsInfo")
public class StudentsInfoController {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private ClassService classService;

    @GetMapping("")
    public String studentSInfo(Model model,
                               @RequestParam(name = "className",defaultValue="all")String className){
        List<studentDTO> studentDtoList = studentScoreService.getStudentDtoList(className);
        List<Class> classList = classService.selectAll();
        model.addAttribute("studentDtoList",studentDtoList);
        model.addAttribute("classList",classList);
        model.addAttribute("className",className);
        return "studentsInfo";
    }
}
