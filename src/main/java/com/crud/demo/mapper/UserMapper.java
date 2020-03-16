package com.crud.demo.mapper;
import org.apache.ibatis.annotations.Param;

import com.crud.demo.entity.User;import org.apache.ibatis.annotations.*;import java.util.List;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();

	;

    @Select("select * from user")
    List<User> getAllUser();

    @Select("select * from user where id=#{id}")
    User getUserById(Integer id);

    @Delete("delete from user where id=#{id}")
    int delUserById(Integer id);

    @Update("update user set gender=#{gender},age=#{age},address=#{address},qq=#{qq},email=#{email} where name=#{name}")
    int UpdateUser(User user);

    @Options(useGeneratedKeys = true, keyProperty = "id")//是指定主键生成的并且主键是id
    @Insert("insert into user(name,gender,age,address,qq,email) values (#{name},#{gender},#{age},#{address},#{qq},#{email})")
    int InsertUser(User user);
}