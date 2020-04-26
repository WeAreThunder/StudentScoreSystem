package com.crud.demo.dto;

import com.crud.demo.entity.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeacherDTO {
    private Teacher teacher;
    private List<CourseDTO> courseDTOList = new ArrayList<>();
}
