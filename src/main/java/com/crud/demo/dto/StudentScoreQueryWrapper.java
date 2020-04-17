package com.crud.demo.dto;

import lombok.Data;

@Data
public class StudentScoreQueryWrapper {
    private String name;
    private String courseName;
    private int maxScore;
    private int minScore;
}
