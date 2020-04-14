package com.crud.demo.controller;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.Teacher;
import com.crud.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacherList")
    public String teacherList(Model model){
        List<Teacher> teacherList = teacherService.getTeacherList();
        model.addAttribute("teachers",teacherList);
        return "teacherList";
    }

    @GetMapping("/teacherAdd")
    public String getTeacherAdd(Model model){
        model.addAttribute("teacher",new Teacher());
        return "teacherAdd";
    }
    @PostMapping("/teacherAdd")
    public String postStudentAdd(@ModelAttribute Teacher teacher){
        teacherService.createTeacher(teacher);
        return "redirect:/teacherList";
    }

    //更新教师信息
    @GetMapping("/teacherUpdate/teacher/{tNumber}")
    public String getTeacherUpdate(@PathVariable("tNumber") String tNumber,
                                   Model model){
        Teacher teacherByNumber = teacherService.getTeacherByNumber(tNumber);
        model.addAttribute("teacher",teacherByNumber);
        return "teacherUpdate";
    }
    //更新后返回
    @PostMapping("/teacherUpdate")
    public String teacherUpdate(@ModelAttribute Teacher teacher){
        teacherService.updateTeacherAndCourseAndScoreByNumber(teacher);
        return "redirect:/teacherList";
    }
    //删除教师信息
    @GetMapping("/teacherDel/{tNumber}")
    public String delTeacherByNumber(@PathVariable("tNumber") String tNumber){
        teacherService.delTeacherByNumber(tNumber);
        return "redirect:/teacherList";
    }
    //查询教师信息
    @GetMapping("/teacher/searchByName")
    public String getStudentsByNumber(@RequestParam("tName") String tName,
                                      Model model){
        List<Teacher> teacherListByName = teacherService.getTeacherListByName(tName);
        System.out.println(teacherListByName.toString());
        model.addAttribute("teachers",teacherListByName);
        return "teacherList";
    }

}
