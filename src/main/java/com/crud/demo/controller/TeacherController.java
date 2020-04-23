package com.crud.demo.controller;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.Teacher;
import com.crud.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacherList")
    public String teacherList(Model model,
                              HttpServletRequest request){
        List<Teacher> teacherList = teacherService.getTeacherList();
        HttpSession session = request.getSession();
        String teacherMessage = (String)session.getAttribute("teacherMessage");
        session.setAttribute("teacherMessage",null);
        model.addAttribute("teachers",teacherList);
        model.addAttribute("teacherMessage",teacherMessage);
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
    public String delTeacherByNumber(@PathVariable("tNumber") String tNumber,
                                     HttpServletRequest request){
        int result = teacherService.delTeacherByNumber(tNumber);
        String teacherMessage = null;
        if (result == 1){
            teacherMessage = "删除成功";
        }else {
            teacherMessage = "课程表中有该教师信息，不可删除；请先删除课程表中相关数据再删除教师";
        }
        HttpSession session = request.getSession();
        session.setAttribute("teacherMessage",teacherMessage);
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
