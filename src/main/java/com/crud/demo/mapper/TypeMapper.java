package com.crud.demo.mapper;

import com.crud.demo.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface TypeMapper {
    @Select("select * from user_type where type = #{type}")
    public UserDTO getByType(@PathVariable("type")String type);
}
