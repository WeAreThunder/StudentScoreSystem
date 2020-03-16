package com.crud.demo.entity;

import lombok.Data;

@Data
public class Teacher {
    private Integer id;
    private String tNumber;
    private String tName;
    private String birthday;
    private String job;
    private String phone;
    private String address;
}
