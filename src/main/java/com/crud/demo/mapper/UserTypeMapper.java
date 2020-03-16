package com.crud.demo.mapper;

import com.crud.demo.entity.UserType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface UserTypeMapper {
    @Select("select * from user_type")
    public List<UserType> getUserTypeList();

    @Select("select * from user_type where type=#{type}")
    public UserType getUserTypeByType(@PathVariable("type")String type);

    @Insert("insert into user_type(type,power) values (#{type},#{power})")
    public void createUserType(@PathVariable("userType") UserType userType);

    @Update("update user_type set type=#{type},power=#{power} where type=#{type}")
    public void updateUserTypeByType(@PathVariable("userType") UserType userType);

    @Delete("delete from user_type where type=#{type}")
    public void delUserTypeByType(@PathVariable("type")String type);
}
