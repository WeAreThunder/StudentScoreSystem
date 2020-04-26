package com.crud.demo.dto;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class studentDTO {
    private Student student;
    private List<StudentScore> studentScoreList = new ArrayList<>();
}
