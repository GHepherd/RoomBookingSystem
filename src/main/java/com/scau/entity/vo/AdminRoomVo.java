package com.scau.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdminRoomVo {
    private Long roomId;
    private String name;
    private String type;
    private Integer capacity;
    private Integer hasProjector;
    private Integer hasSound;
    private Integer hasNetwork;
    private BigDecimal price;
    private String description;
    private BigDecimal area;
    private Integer status;
    private Integer startTime;
    private Integer endTime;
}
