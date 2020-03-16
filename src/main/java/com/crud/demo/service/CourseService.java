package com.crud.demo.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.crud.demo.entity.Course;
import com.crud.demo.mapper.CourseMapper;

import java.util.List;

@Service
public class CourseService{

    @Resource
    private CourseMapper courseMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return courseMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Course record) {
        return courseMapper.insert(record);
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

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    public List<Course> selectAllByCourseNameLike(String likeCourseName) {
        return courseMapper.selectAllByCourseNameLike(likeCourseName);
    }
}
