package com.crud.demo.service;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.Teacher;
import com.crud.demo.mapper.StudentMapper;
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
    @Autowired
    private StudentMapper studentMapper;


    
    public int deleteByPrimaryKey(Integer id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        studentScoreMapper.deleteByCourseNumber(course.getCourseNumber());
        courseMapper.deleteByPrimaryKey(id);
        return 1;
    }

    
    public int insert(Course course) {
        Teacher teacher = teacherMapper.getTeacherByNumber(course.getTNumber());
        course.setTName(teacher.getTName());
        //创建课程时，建立该班级下所有学生的成绩记录
        String className = course.getClassName();
        List<Student> studentList = studentMapper.selectByClassName(className);
        for (Student student : studentList) {
            StudentScore studentScore = new StudentScore();
            studentScore.setCourseNumber(course.getCourseNumber());
            studentScore.setCourseName(course.getCourseName());
            studentScore.setSNumber(student.getSNumber());
            studentScore.setSName(student.getSName());
            studentScore.setTNumber(teacher.getTNumber());
            studentScore.setTName(teacher.getTName());
            studentScoreMapper.insertSelective(studentScore);
        }
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
        courseMapper.updateByPrimaryKeySelective(course);
        Course dbCourse = courseMapper.selectByPrimaryKey(course.getId());
        List<StudentScore> studentScoreList = studentScoreMapper.selectByCourseNumber(dbCourse.getCourseNumber());
        //当课程名发生变更时，遍历成绩信息，更改课程名
        if (course.getCourseName().equals(dbCourse.getCourseName())){
            for (StudentScore studentScore : studentScoreList) {
                studentScore.setCourseName(dbCourse.getCourseName());
                studentScoreMapper.updateByPrimaryKey(studentScore);
            }
        }
    }

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    public List<Course> selectAllByCourseNameLike(String likeCourseName) {
        return courseMapper.selectAllByCourseNameLike(likeCourseName);
    }
}
