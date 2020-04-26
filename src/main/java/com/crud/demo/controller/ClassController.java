package com.crud.demo.controller;

import com.crud.demo.entity.Class;
import com.crud.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/classList")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("")
    public String classList(Model model,
                            HttpServletRequest request){
        List<Class> classList = classService.selectAll();
        model.addAttribute("classList",classList);

        HttpSession session = request.getSession();
        String classMessage = (String)session.getAttribute("classMessage");
        model.addAttribute("classMessage",classMessage);
        session.setAttribute("classMessage",null);
        return "classList";
    }

    //添加
    @GetMapping("/add")
    public String getAddClass(Model model){
        Class dbClass = new Class();
        model.addAttribute("dbClass",dbClass);
        return "classAdd";
    }
    @PostMapping("/add")
    public String postAddClass(@ModelAttribute Class dbClass){
        classService.insertSelective(dbClass);
        return "redirect:/classList";
    }

    //更新
    @GetMapping("/update/{id}")
    public String getUpdateClass(@PathVariable("id") Integer id,
                                 Model model){
        Class dbClass = classService.selectByPrimaryKey(id);
        model.addAttribute("dbClass",dbClass);
        return "classUpdate";
    }
    @PostMapping("/update")
    public String postUpdateClass(@ModelAttribute Class dbClass){
        classService.updateByPrimaryKey(dbClass);
        return "redirect:/classList";
    }

    //删除
    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable("id") Integer id,
                              HttpServletRequest request){
        int result = classService.deleteByPrimaryKey(id);
        String classMessage = null;
        if (result == 1){
            classMessage = "删除成功";
        }else {
            classMessage = "课程表或者学生表中有该班级信息，不可删除；请先删除课程表和学生表中相关数据再删除班级";
        }
        HttpSession session = request.getSession();
        session.setAttribute("classMessage",classMessage);
        return "redirect:/classList";
    }

}
