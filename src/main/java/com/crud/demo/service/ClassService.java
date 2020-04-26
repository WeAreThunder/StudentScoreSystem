package com.crud.demo.service;

import com.crud.demo.entity.Course;
import com.crud.demo.entity.Student;
import com.crud.demo.mapper.CourseMapper;
import com.crud.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.crud.demo.mapper.ClassMapper;
import com.crud.demo.entity.Class;

import java.util.List;

@Service
public class ClassService{

    @Resource
    private ClassMapper classMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        Class dbClass = classMapper.selectByPrimaryKey(id);
        //如果班级在课程表中存在，不允许删除
        List<Course> courseList = courseMapper.selectByClassName(dbClass.getClassName());
        if (courseList.size() > 0){
            return 0;
        }
        //如果班级在学生表中存在，不允许删除
        List<Student> studentListByClassName = studentMapper.getStudentListByClassName(dbClass.getClassName());
        if (studentListByClassName.size() > 0){
            return 0;
        }
        return classMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Class record) {
        return classMapper.insert(record);
    }

    
    public int insertSelective(Class record) {
        return classMapper.insertSelective(record);
    }

    
    public Class selectByPrimaryKey(Integer id) {
        return classMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Class record) {
        return classMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Class record) {
        Class dbClass = selectByPrimaryKey(record.getId());
        //更新学生表中的班级名
        List<Student> studentListByClassName = studentMapper.getStudentListByClassName(dbClass.getClassName());
        for (Student student : studentListByClassName) {
            student.setClassName(record.getClassName());
            studentMapper.updateByNumber(student);
        }
        //更新课程表中的班级名
        List<Course> courseList = courseMapper.selectByClassName(dbClass.getClassName());
        for (Course course : courseList) {
            course.setClassName(record.getClassName());
            courseMapper.updateByPrimaryKey(course);
        }
        //更新班级表中的班级名
        return classMapper.updateByPrimaryKey(record);
    }

    public List<Class> selectAll() {
        return classMapper.selectAll();
    }


}
