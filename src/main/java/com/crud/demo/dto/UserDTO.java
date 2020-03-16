package com.crud.demo.dto;

import com.crud.demo.entity.Users;
import lombok.Data;

@Data
public class UserDTO {
    private Users users;
    private String type;
    private Integer power;
}
