package com.crud.demo.mapper;

import com.crud.demo.entity.Teacher;
import com.crud.demo.entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface UsersMapper {
    @Select("select * from users")
    public List<Users> getAllUsers();

    @Select("select * from users where name = #{name}")
    public Users getUsersPwdByName(@PathVariable("name")String name);

    @Select("select * from users where name like CONCAT('%',#{name},'%') ")
    public List<Users> getUsersListByName(@PathVariable("name") String name);

    @Insert("insert into users(name,password,type,crater_time) values (#{name},#{password},#{type},#{craterTime})")
    public void createUsers(Users users);

    @Update("update users set name=#{name},password=#{password},type=#{type} where name=#{name}")
    public void updateUsersByName(Users users);

    @Delete("delete from users where name=#{name}")
    public void delUsersByName(@PathVariable("name")String name);
}
