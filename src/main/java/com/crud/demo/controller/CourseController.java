package com.crud.demo.controller;

import com.crud.demo.entity.*;
import com.crud.demo.entity.Class;
import com.crud.demo.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentScoreService studentScoreService;

    @GetMapping("courseList")
    public String courseList(Model model,
                             HttpServletRequest request){
        HttpSession session = request.getSession();
        String courseMessage = (String)session.getAttribute("courseMessage");
        session.setAttribute("courseMessage",null);
        List<Course> courses = courseService.selectAll();
        model.addAttribute("courses",courses);
        model.addAttribute("courseMessage",courseMessage);
        return "courseList";
    }

    @GetMapping("/courseAdd")
    public String getCourseAdd(Model model){
        List<Class> classList = classService.selectAll();
        List<Teacher> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("classList",classList);
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
        List<Teacher> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacherList",teacherList);
        List<Class> classList = classService.selectAll();
        model.addAttribute("classList",classList);
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
    public String delCourseById(@PathVariable("id")Integer id,
                                HttpServletRequest request){
        int result = courseService.deleteByPrimaryKey(id);
        String courseMessage = null;
        if (result == 1){
            courseMessage = "删除成功";
        }else {
            courseMessage = "成绩表中有该课程信息，不可删除；请先删除成绩表中相关数据，再删除课程";
        }
        HttpSession session = request.getSession();
        session.setAttribute("courseMessage",courseMessage);
        return "redirect:/courseList";
    }

    @GetMapping("/course/searchByName")
    public String getCoursesByName(@RequestParam("courseName") String courseName,
                                   Model model){
        List<Course> courses = courseService.selectAllByCourseNameLike(courseName);
        model.addAttribute("courses",courses);
        return "courseList";
    }

    @GetMapping("/course/info/{id}")
    public String showCourseInfo(@PathVariable("id") Integer id,
                                 Model model){
        Course course = courseService.selectByPrimaryKey(id);
        List<StudentScore> studentScoreList = studentScoreService.selectByCourseNumber(course.getCourseNumber());
        List<Student> studentList = new ArrayList<>();
        for (StudentScore studentScore :
                studentScoreList) {
            Student studentByNumber = studentService.getStudentByNumber(studentScore.getSNumber());
            studentList.add(studentByNumber);
        }
        Teacher teacher = teacherService.getTeacherByNumber(course.getTNumber());
        model.addAttribute("course",course);
        model.addAttribute("teacher",teacher);
        model.addAttribute("studentScoreList",studentScoreList);
        model.addAttribute("studentList",studentList);
        return "courseInfo";
    }

}
