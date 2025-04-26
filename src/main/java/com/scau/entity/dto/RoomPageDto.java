package com.scau.entity.dto;

import lombok.Data;

@Data
public class RoomPageDto {
    private Integer page;
    private Integer pageSize;
    private String keyword;
}
