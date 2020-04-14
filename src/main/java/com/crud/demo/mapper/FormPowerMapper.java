package com.crud.demo.mapper;

import com.crud.demo.entity.FormPower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository//加了这个，service调用mapper时就不报错，不加也没关系
public interface FormPowerMapper {
    @Select("select * from form_power")
    public List<FormPower> getFormPowerList();

    @Select("select * from form_power where form=#{form}")
    public FormPower getFormPowerByForm(@PathVariable("form")String form);
    //set是MySQL中的关键字，以后设置的时候尽量避开
    @Update("update form_power set form=#{form},`set`=#{set} where form=#{form}")
    public void updateFormPower(@PathVariable("formPower")FormPower formPower);
}
