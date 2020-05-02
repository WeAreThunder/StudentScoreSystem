package com.crud.demo.mapper;

import com.crud.demo.entity.Student;import org.apache.ibatis.annotations.Delete;import org.apache.ibatis.annotations.Insert;import org.apache.ibatis.annotations.Select;import org.apache.ibatis.annotations.Update;import org.springframework.web.bind.annotation.PathVariable;import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    @Select("select * from student order by s_number")
    List<Student> getStudentList();

    @Select("select * from student where class_name = #{className} order by s_number")
    List<Student> getStudentListByClassName(@PathVariable("className") String className);

    @Select("select * from student where s_number = #{sNumber}")
    Student getStudentByNumber(@PathVariable("sNumber") String sNumber);

    @Select("select * from student where s_name like CONCAT('%',#{sName},'%') ")
    List<Student> getStudentListByName(@PathVariable("sName") String sName);

    @Insert("insert into student(s_number,s_name,sex,birthday,class_name,phone,address) " +
            "values (#{sNumber},#{sName},#{sex},#{birthday},#{className},#{phone},#{address},#{avatar})")
    void create(@PathVariable("student") Student student);

    @Update("update student set s_number=#{sNumber},s_name=#{sName},sex=#{sex},birthday=#{birthday}," +
            "class_name=#{className},phone=#{phone},address=#{address},avatar=#{avatar}  where s_number=#{sNumber}")
    void updateByNumber(@PathVariable("student") Student student);

    @Delete("delete from student where s_number=#{sNumber}")
    int delByNumber(@PathVariable("sNumber") String sNumber);
}