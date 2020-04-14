package com.crud.demo.controller;

import com.crud.demo.entity.Course;
import com.crud.demo.service.CourseService;
import com.crud.demo.service.TeacherService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("courseList")
    public String courseList(Model model){
        List<Course> courses = courseService.selectAll();
        model.addAttribute("courses",courses);
        return "courseList";
    }

    @GetMapping("/courseAdd")
    public String getCourseAdd(Model model){
        model.addAttribute("course",new Course());
        return "courseAdd";
    }
    @PostMapping("courseAdd")
    public String postCourseAdd(@ModelAttribute Course course){
        courseService.insert(course);
        return "redirect:/courseList";
    }
    @GetMapping("/courseUpdate/course/{id}")
    public String getCourseUpdate(@PathVariable("id") Integer id,
                                  Model model){
        Course course = courseService.selectByPrimaryKey(id);
        model.addAttribute("course",course);
        return "courseUpdate";
    }
    @PostMapping("courseUpdate")
    public String postCourseUpdate(@ModelAttribute Course course){
        courseService.updateCourseAndScoreById(course);
        return "redirect:/courseList";
    }
    @GetMapping("/courseDel/{id}")
    public String delCourseById(@PathVariable("id")Integer id){
        courseService.deleteByPrimaryKey(id);
        return "redirect:/courseList";
    }

    @GetMapping("/course/searchByName")
    public String getCoursesByName(@RequestParam("courseName") String courseName,
                                   Model model){
        List<Course> courses = courseService.selectAllByCourseNameLike(courseName);
        model.addAttribute("courses",courses);
        return "courseList";
    }


}
