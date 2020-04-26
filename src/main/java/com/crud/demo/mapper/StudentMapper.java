package com.crud.demo.mapper;

import com.crud.demo.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface StudentMapper {
    @Select("select * from student order by s_number")
    public List<Student> getStudentList();

    @Select("select * from student where class_name = #{className} order by s_number")
    public List<Student> getStudentListByClassName(@PathVariable("className") String className);

    @Select("select * from student where s_number = #{sNumber}")
    public Student getStudentByNumber(@PathVariable("sNumber") String sNumber);

    @Select("select * from student where s_name like CONCAT('%',#{sName},'%') ")
    public List<Student> getStudentListByName(@PathVariable("sName") String sName);

    @Insert("insert into student(s_number,s_name,sex,birthday,class_name,phone,address) " +
            "values (#{sNumber},#{sName},#{sex},#{birthday},#{className},#{phone},#{address})")
    public void create(@PathVariable("student") Student student);

    @Update("update student set s_number=#{sNumber},s_name=#{sName},sex=#{sex},birthday=#{birthday}," +
            "class_name=#{className},phone=#{phone},address=#{address} where s_number=#{sNumber}")
    public void updateByNumber(@PathVariable("student") Student student);

    @Delete("delete from student where s_number=#{sNumber}")
    public int delByNumber(@PathVariable("sNumber") String sNumber);
}
