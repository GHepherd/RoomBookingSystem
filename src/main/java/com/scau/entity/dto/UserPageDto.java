package com.scau.entity.dto;

import lombok.Data;

@Data
public class UserPageDto {
    private Integer page;
    private Integer pageSize;
    private Integer status;
    private String keyword;
}
