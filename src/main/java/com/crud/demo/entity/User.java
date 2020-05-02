package com.crud.demo.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String name;

    private String gender;

    private Integer age;

    private String address;

    private String qq;

    private String email;

    private String avatar;
}