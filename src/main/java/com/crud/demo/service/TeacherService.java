package com.crud.demo.service;

import com.crud.demo.entity.Course;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.Teacher;
import com.crud.demo.mapper.CourseMapper;
import com.crud.demo.mapper.StudentScoreMapper;
import com.crud.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentScoreMapper studentScoreMapper;
    @Autowired
    private CourseMapper courseMapper;

    public List<Teacher> getTeacherList() {
        return teacherMapper.getTeacherList();
    }

    public Teacher getTeacherByNumber(String tNumber) {
        return teacherMapper.getTeacherByNumber(tNumber);
    }

    public List<Teacher> getTeacherListByName(String tName) {
        return teacherMapper.getTeacherListByName(tName);
    }

    public void createTeacher(Teacher teacher) {
        teacherMapper.createTeacher(teacher);
    }

    public void updateTeacherByNumber(Teacher teacher) {
        teacherMapper.updateTeacherByNumber(teacher);
    }

    public void updateTeacherAndCourseAndScoreByNumber(Teacher teacher) {
        teacherMapper.updateTeacherByNumber(teacher);
        Teacher dbTeacher = teacherMapper.getTeacherByNumber(teacher.getTNumber());
        List<StudentScore> studentScoreList = studentScoreMapper.selectByTNumber(dbTeacher.getTNumber());
        //遍历成绩信息，更改教师名
        for (StudentScore studentScore : studentScoreList) {
            studentScore.setTName(dbTeacher.getTName());
            studentScoreMapper.updateByPrimaryKey(studentScore);
        }
        //遍历课程信息，更改教师名
        List<Course> courseList = courseMapper.selectByTNumber(dbTeacher.getTNumber());
        for (Course course : courseList) {
            course.setTName(dbTeacher.getTName());
            courseMapper.updateByPrimaryKey(course);
        }

    }

    public int delTeacherByNumber(String tNumber) {
        Teacher teacher = teacherMapper.getTeacherByNumber(tNumber);
        List<Course> courseList = courseMapper.selectByTNumber(teacher.getTNumber());
        if (courseList.size() > 0){
            return 0;
        }else {
            teacherMapper.delTeacherByNumber(tNumber);
            return 1;
        }
    }

    public int deleteByPrimaryKey(Integer id) {
        return teacherMapper.deleteByPrimaryKey(id);
    }

    public int insert(Teacher record) {
        return teacherMapper.insert(record);
    }

    public int insertSelective(Teacher record) {
        return teacherMapper.insertSelective(record);
    }

    public Teacher selectByPrimaryKey(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Teacher record) {
        return teacherMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Teacher record) {
        return teacherMapper.updateByPrimaryKey(record);
    }
}

