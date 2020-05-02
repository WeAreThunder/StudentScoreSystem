package com.crud.demo.service;

import com.crud.demo.entity.User;
import com.crud.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public List<User> userList() {
        return userMapper.getAllUser();
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int delete(Integer id) {
        return userMapper.delUserById(id);
    }

    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public User getById(Integer id) {
        return userMapper.getUserById(id);
    }

    public List<User> userListByNew() {
        return userMapper.selectAll();
    }

    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }
}

