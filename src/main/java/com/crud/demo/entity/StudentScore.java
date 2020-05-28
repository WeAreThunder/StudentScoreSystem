package com.crud.demo.entity;

import lombok.Data;

@Data
public class StudentScore {
    private Integer id;

    private String courseNumber;

    /**
     * 课程
     */
    private String courseName;

    private String sNumber;

    private String sName;

    private Integer scoreA;

    private Integer scoreB;

    private Integer scoreC;

    private Integer score;

    private String tNumber;

    private String tName;
}