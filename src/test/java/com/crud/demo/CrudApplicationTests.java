package com.crud.demo;

import com.crud.demo.entity.Class;
import com.crud.demo.mapper.ClassMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CrudApplicationTests {
    @Autowired
    private ClassMapper classMapper;

    @Test
    void contextLoads() {
        List<Class> classList = classMapper.selectAll();
        Map<Class,Integer> classDtoMap = new HashMap<>();

        for (Class dbClass : classList) {
            classDtoMap.put(dbClass, 20);
        }

        System.out.println(classDtoMap);
    }

}
