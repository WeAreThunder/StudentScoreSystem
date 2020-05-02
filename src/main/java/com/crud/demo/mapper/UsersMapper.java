package com.crud.demo.mapper;

import com.crud.demo.entity.Users;import org.apache.ibatis.annotations.Delete;import org.apache.ibatis.annotations.Insert;import org.apache.ibatis.annotations.Select;import org.apache.ibatis.annotations.Update;import org.springframework.web.bind.annotation.PathVariable;import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    @Select("select * from users")
    List<Users> getAllUsers();

    @Select("select * from users where name = #{name}")
    Users getUsersPwdByName(@PathVariable("name") String name);

    @Select("select * from users where name like CONCAT('%',#{name},'%') ")
    List<Users> getUsersListByName(@PathVariable("name") String name);

    @Insert("insert into users(name,password,type,crater_time) values (#{name},#{password},#{type},#{craterTime})")
    void createUsers(Users users);

    @Update("update users set name=#{name},password=#{password},type=#{type} where name=#{name}")
    void updateUsersByName(Users users);

    @Delete("delete from users where name=#{name}")
    void delUsersByName(@PathVariable("name") String name);
}