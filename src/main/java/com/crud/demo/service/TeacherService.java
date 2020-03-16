package com.crud.demo.service;

import com.crud.demo.entity.Teacher;
import com.crud.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public List<Teacher> getTeacherList(){return teacherMapper.getTeacherList();}

    public Teacher getTeacherByNumber(String tNumber){return teacherMapper.getTeacherByNumber(tNumber);}

    public List<Teacher> getTeacherListByName(String tName){return teacherMapper.getTeacherListByName(tName);}

    public void createTeacher(Teacher teacher){teacherMapper.createTeacher(teacher);}

    public void updateTeacherByNumber(Teacher teacher){teacherMapper.updateTeacherByNumber(teacher);}

    public void delTeacherByNumber(String tNumber){teacherMapper.delTeacherByNumber(tNumber);}

}
