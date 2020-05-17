package com.crud.demo.service;

import com.crud.demo.dto.UserDTO;
import com.crud.demo.entity.Users;
import com.crud.demo.mapper.TypeMapper;
import com.crud.demo.mapper.UsersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private TypeMapper typeMapper;

    public List<Users> getAllUsers() {
        return usersMapper.getAllUsers();
    }

    public Users getUsers(String name) {
        return usersMapper.getUsersPwdByName(name);
    }

    public List<Users> getUsersListByName(String name) {
        return usersMapper.getUsersListByName(name);
    }

    public void createUsers(Users users) {
        usersMapper.createUsers(users);
    }

    ;

    public void updateUsersByName(Users users) {
        usersMapper.updateUsersByName(users);
    }

    ;

    public void delUsersByName(String name) {
        usersMapper.delUsersByName(name);
    }

    //获取用户权限和用户
    public UserDTO getUserDTOByName(String name) {
        Users users = usersMapper.getUsersPwdByName(name);
        if (users == null){
            return null;
        }
        UserDTO userDTO = typeMapper.getByType(users.getType());
        userDTO.setUsers(users);
        return userDTO;
    }

    public int deleteByPrimaryKey(Integer id) {
        return usersMapper.deleteByPrimaryKey(id);
    }

    public int insert(Users record) {
        return usersMapper.insert(record);
    }

    public int insertSelective(Users record) {
        return usersMapper.insertSelective(record);
    }

    public Users selectByPrimaryKey(Integer id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Users record) {
        return usersMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Users record) {
        return usersMapper.updateByPrimaryKey(record);
    }
}


