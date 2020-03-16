package com.crud.demo.mapper;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface TeacherMapper {
    @Select("select * from teacher")
    public List<Teacher> getTeacherList();

    @Select("select * from teacher where t_number=#{tNumber}")
    public Teacher getTeacherByNumber(@PathVariable("tNumber")String tNumber);

    @Select("select * from teacher where t_name like CONCAT('%',#{tName},'%') ")
    public List<Teacher> getTeacherListByName(@PathVariable("tName") String tName);

    @Insert("insert into teacher(t_number,t_name,birthday,job,phone,address) values  (#{tNumber},#{tName},#{birthday},#{job},#{phone},#{address}) ")
    public void createTeacher(@PathVariable("teacher")Teacher teacher);

    @Update("update teacher set t_number=#{tNumber},t_name=#{tName},birthday=#{birthday},job=#{job},phone=#{phone},address=#{address} where t_number=#{tNumber}")
    public void updateTeacherByNumber(@PathVariable("teacher")Teacher teacher);

    @Delete("delete from teacher where t_number=#{tNumber}")
    public void delTeacherByNumber(@PathVariable("tNumber")String tNumber);

}
