package com.scau.entity.vo;

import lombok.Data;

@Data
public class AdminGetUsersVo {
    private Long userId;
    private String username;
    private String name;
    private String phone;
    private String company;
    private Integer status;
    private String createTime;
}
