package com.crud.demo.service;

import com.crud.demo.entity.UserType;
import com.crud.demo.mapper.UserTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {
    @Autowired
    private UserTypeMapper userTypeMapper;

    public List<UserType> userTypeList(){return userTypeMapper.getUserTypeList();}

    public UserType getUserTypeByType(String type){return userTypeMapper.getUserTypeByType(type);}

    public void createUserType(UserType userType){userTypeMapper.createUserType(userType);}

    public void updateUserTypeByType(UserType userType){userTypeMapper.updateUserTypeByType(userType);}

    public void delUserTypeByType(String type){userTypeMapper.delUserTypeByType(type);}

}
