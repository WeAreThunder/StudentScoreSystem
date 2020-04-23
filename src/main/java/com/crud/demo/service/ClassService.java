package com.crud.demo.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.crud.demo.mapper.ClassMapper;
import com.crud.demo.entity.Class;

import java.util.List;

@Service
public class ClassService{

    @Resource
    private ClassMapper classMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return classMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Class record) {
        return classMapper.insert(record);
    }

    
    public int insertSelective(Class record) {
        return classMapper.insertSelective(record);
    }

    
    public Class selectByPrimaryKey(Integer id) {
        return classMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Class record) {
        return classMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Class record) {
        return classMapper.updateByPrimaryKey(record);
    }

    public List<Class> selectAll() {
        return classMapper.selectAll();
    }
}
