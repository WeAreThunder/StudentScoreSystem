package com.crud.demo.controller;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.service.StudentScoreService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TableImageController {

    @Autowired
    private StudentScoreService studentScoreService;

    @RequestMapping("/TableImage/{number}")
    public List<StudentScore> showCourseTableImage(@PathVariable("number") String number){
        List<StudentScore> studentScoreList = studentScoreService.selectByCourseNumber(number);
        System.out.println(studentScoreList);
        return studentScoreList;
    }

}
