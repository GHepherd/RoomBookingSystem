package com.scau.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class RoomCreateDto {
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
