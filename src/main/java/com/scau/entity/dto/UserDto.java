package com.scau.entity.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String role;
    private String company;
}
