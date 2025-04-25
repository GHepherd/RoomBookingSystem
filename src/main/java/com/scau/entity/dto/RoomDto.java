package com.scau.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {
    private String name;
    private String type;
    private Integer capacity;
    private BigDecimal area;
    private Integer hasProjector;
    private Integer hasSound;
    private Integer hasNetwork;
    private BigDecimal price;
    private String description;
    private Integer status;
    private Integer startTime;
    private Integer endTime;
}
