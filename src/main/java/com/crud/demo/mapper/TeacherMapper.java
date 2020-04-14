package com.crud.demo.mapper;

import com.crud.demo.entity.Teacher;import org.apache.ibatis.annotations.Delete;import org.apache.ibatis.annotations.Insert;import org.apache.ibatis.annotations.Select;import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;import java.util.List;
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    @Select("select * from teacher")
    List<Teacher> getTeacherList();

    @Select("select * from teacher where t_number=#{tNumber}")
    Teacher getTeacherByNumber(@PathVariable("tNumber") String tNumber);

    @Select("select * from teacher where t_name like CONCAT('%',#{tName},'%') ")
    List<Teacher> getTeacherListByName(@PathVariable("tName") String tName);

    @Insert("insert into teacher(t_number,t_name,birthday,job,phone,address) values  (#{tNumber},#{tName},#{birthday},#{job},#{phone},#{address}) ")
    void createTeacher(@PathVariable("teacher") Teacher teacher);

    @Update("update teacher set t_number=#{tNumber},t_name=#{tName},birthday=#{birthday},job=#{job},phone=#{phone},address=#{address} where t_number=#{tNumber}")
    void updateTeacherByNumber(@PathVariable("teacher") Teacher teacher);

    @Delete("delete from teacher where t_number=#{tNumber}")
    void delTeacherByNumber(@PathVariable("tNumber") String tNumber);
}