package com.crud.demo.service;

import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.Teacher;
import com.crud.demo.mapper.StudentScoreMapper;
import com.crud.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.crud.demo.entity.Course;
import com.crud.demo.mapper.CourseMapper;

import java.util.List;

@Service
public class CourseService{

    @Resource
    private CourseMapper courseMapper;
    @Autowired
    private StudentScoreMapper studentScoreMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        List<StudentScore> studentScoreList = studentScoreMapper.selectByCourseNumber(course.getCourseNumber());
        if (studentScoreList.size() > 0){
            return 0;
        }else {
            courseMapper.deleteByPrimaryKey(id);
            return 1;
        }
    }

    
    public int insert(Course course) {
        Teacher teacher = teacherMapper.getTeacherByNumber(course.getTNumber());
        course.setTName(teacher.getTName());
        return courseMapper.insert(course);
    }

    
    public int insertSelective(Course record) {
        return courseMapper.insertSelective(record);
    }

    
    public Course selectByPrimaryKey(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Course record) {
        return courseMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Course record) {
        return courseMapper.updateByPrimaryKey(record);
    }

    public void updateCourseAndScoreById(Course course){
        //根据教师号填充教师名
        Teacher teacher = teacherMapper.getTeacherByNumber(course.getTNumber());
        course.setTName(teacher.getTName());

        courseMapper.updateByPrimaryKey(course);

        Course dbCourse = courseMapper.selectByPrimaryKey(course.getId());
        List<StudentScore> studentScoreList = studentScoreMapper.selectByCourseNumber(dbCourse.getCourseNumber());
        //遍历成绩信息，更改课程名
        for (StudentScore studentScore : studentScoreList) {
            studentScore.setCourseName(dbCourse.getCourseName());
            studentScoreMapper.updateByPrimaryKey(studentScore);
        }
    }

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    public List<Course> selectAllByCourseNameLike(String likeCourseName) {
        return courseMapper.selectAllByCourseNameLike(likeCourseName);
    }
}
