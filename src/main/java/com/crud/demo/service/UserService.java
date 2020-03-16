package com.crud.demo.service;

import com.crud.demo.entity.User;
import com.crud.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public List<User> userList(){
        return userMapper.getAllUser();
    }

    public int insert(User user){
        return userMapper.InsertUser(user);
    }

    public int delete(Integer id){
        return userMapper.delUserById(id);
    }

    public int update(User user){ return userMapper.UpdateUser(user); }

    public User getById(Integer id){
        return userMapper.getUserById(id);
    }

    public List<User> userListByNew(){ return userMapper.selectAll();}
}
