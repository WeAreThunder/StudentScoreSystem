package com.crud.demo.mapper;
import org.apache.ibatis.annotations.Param;

import com.crud.demo.dto.StudentScoreQueryWrapper;import com.crud.demo.entity.StudentScore;import org.apache.ibatis.annotations.*;import org.springframework.web.bind.annotation.PathVariable;import java.util.List;

public interface StudentScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentScore record);

    int insertSelective(StudentScore record);

    StudentScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentScore record);

    int updateByPrimaryKey(StudentScore record);

    int deleteBySNumber(@Param("sNumber") String sNumber);

    int deleteByCourseNumber(@Param("courseNumber")String courseNumber);



    List<StudentScore> selectBySNumber(@Param("sNumber") String sNumber);

    List<StudentScore> selectByCourseNumber(@Param("courseNumber") String courseNumber);

    List<StudentScore> selectByTNumber(@Param("tNumber") String tNumber);

    List<StudentScore> selectByAll(StudentScore studentScore);

    List<StudentScore> selectByStudentScoreQueryWrapper(StudentScoreQueryWrapper studentScoreQueryWrapper);

    //查询所有成绩表信息
    @Select("select * from student_score")
    List<StudentScore> getStudentScoreList();

    //根据id查询学生表
    @Select("select * from student_score where id = #{id}")
    StudentScore getStudentScoreById(@PathVariable("id") Integer id);

    //根据学生名模糊查询成绩表
    @Select("select * from student_score where s_name like CONCAT('%',#{sName},'%') ")
    List<StudentScore> getStudentScoreListByName(@PathVariable("sName") String sName);

    @Insert("insert into student_score(course_number,course_name,s_number,s_name,score,t_number,t_name) values (#{courseNumber},#{courseName},#{sNumber},#{sName},#{score},#{tNumber},#{tName})")
    void createStudentScore(@PathVariable("studentScore") StudentScore studentScore);

    @Update("update student_score set course_number=#{courseNumber},course_name=#{courseName},s_number=#{sNumber},s_name=#{sName},score=#{score},t_number=#{tNumber},t_name=#{tName} where id=#{id}")
    void updateStudentScoreById(@PathVariable("studentScore") StudentScore studentScore);

    @Delete("delete from student_score where id=#{id}")
    int delStudentScoreById(@PathVariable("id") Integer id);
}