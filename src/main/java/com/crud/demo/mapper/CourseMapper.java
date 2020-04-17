package com.crud.demo.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.crud.demo.entity.Course;
@Mapper
public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectAll();

    List<Course> selectAllByCourseNameLike(@Param("likeCourseName")String likeCourseName);

    List<Course> selectByTNumber(@Param("tNumber")String tNumber);


    List<Course> selectByCourseNumber(@Param("courseNumber")String courseNumber);



}