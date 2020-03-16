package com.crud.demo.entity;

import lombok.Data;

@Data
public class Users {
    private Integer id;
    private String name;
    private String password;
    private String type;
    private Long craterTime;
}
