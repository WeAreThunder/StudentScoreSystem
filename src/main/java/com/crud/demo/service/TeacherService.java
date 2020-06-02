package com.crud.demo.service;

import com.crud.demo.config.avatarUpload;
import com.crud.demo.dto.CourseDTO;
import com.crud.demo.dto.TeacherDTO;
import com.crud.demo.entity.Course;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.Teacher;
import com.crud.demo.mapper.CourseMapper;
import com.crud.demo.mapper.StudentScoreMapper;
import com.crud.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        teacherMapper.updateByPrimaryKeySelective(teacher);
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
        //删除教师头像
        avatarUpload avatarUpload = new avatarUpload();
        avatarUpload.removeAvatarByFileName(teacher.getAvatar());

        List<Course> courseList = courseMapper.selectByTNumber(teacher.getTNumber());
        if (courseList.size() > 0) {
            return 0;
        } else {
            teacherMapper.delTeacherByNumber(tNumber);
            return 1;
        }
    }

    public List<TeacherDTO> getTeacherDTOListByJob(String job) {
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        List<Teacher> teacherList = new ArrayList<>();
        if (job.equals("all")) {
            teacherList = teacherMapper.getTeacherList();
        } else {
            teacherList = teacherMapper.selectByJob(job);
        }

        List<Course> courseList = courseMapper.selectAll();
        List<StudentScore> studentScoreList = studentScoreMapper.getStudentScoreList();

        for (Teacher teacher : teacherList) {
            TeacherDTO teacherDTO = new TeacherDTO();
            List<CourseDTO> courseDTOList = new ArrayList<>();
            //添加教师到Dto中
            teacherDTO.setTeacher(teacher);
            //添加courseDto
            for (Course course : courseList) {
                CourseDTO courseDTO = new CourseDTO();
                int studentCount = 0;
                if (course.getTNumber().equals(teacher.getTNumber())) {
                    courseDTO.setCourse(course);
                    //将学习人数添加到dto中
                    for (StudentScore studentScore : studentScoreList) {
                        if (studentScore.getCourseNumber().equals(course.getCourseNumber())) {
                            studentCount++;
                        }
                    }
                }
                courseDTO.setStudentCount(studentCount);
                if (courseDTO.getCourse() != null)
                    courseDTOList.add(courseDTO);
            }
            teacherDTO.setCourseDTOList(courseDTOList);
            teacherDTOList.add(teacherDTO);
        }
        return teacherDTOList;
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


