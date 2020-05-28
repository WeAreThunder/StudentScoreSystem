package com.crud.demo.mapper;

import com.crud.demo.entity.Teacher;import org.apache.ibatis.annotations.*;import org.springframework.web.bind.annotation.PathVariable;import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    List<Teacher> selectByJob(@Param("job") String job);


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