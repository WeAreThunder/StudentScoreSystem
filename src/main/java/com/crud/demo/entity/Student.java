package com.crud.demo.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Student {
    private Integer id;

    private String sNumber;

    private String sName;

    private String sex;

    private String birthday;

    private String className;

    private String phone;

    private String address;

    private String avatar;
}