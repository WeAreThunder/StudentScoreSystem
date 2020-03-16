package com.crud.demo.mapper;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface StudentScoreMapper {
    //查询所有成绩表信息
    @Select("select * from student_score")
    public List<StudentScore> getStudentScoreList();
    //根据id查询学生表
    @Select("select * from student_score where id = #{id}")
    public StudentScore getStudentScoreById(@PathVariable("id") Integer id);
    //根据学生名模糊查询成绩表
    @Select("select * from student_score where s_name like CONCAT('%',#{sName},'%') ")
    public List<StudentScore> getStudentScoreListByName(@PathVariable("sName") String sName);

    @Insert("insert into student_score(course_number,course_name,s_number,s_name,score,t_number,t_name) values (#{courseNumber},#{courseName},#{sNumber},#{sName},#{score},#{tNumber},#{tName})")
    public void createStudentScore(@PathVariable("studentScore") StudentScore studentScore);

    @Update("update student_score set course_number=#{courseNumber},course_name=#{courseName},s_number=#{sNumber},s_name=#{sName},score=#{score},t_number=#{tNumber},t_name=#{tName} where id=#{id}")
    public void updateStudentScoreById(@PathVariable("studentScore")StudentScore studentScore);

    @Delete("delete from student_score where id=#{id}")
    public int delStudentScoreById(@PathVariable("id") Integer id);
}
