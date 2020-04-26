package com.crud.demo.dto;

import com.crud.demo.entity.Course;
import lombok.Data;

@Data
public class CourseDTO {
    private Course course;
    private int studentCount;
}
