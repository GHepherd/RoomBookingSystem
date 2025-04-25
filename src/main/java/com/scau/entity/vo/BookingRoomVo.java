package com.scau.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class BookingRoomVo {
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
    private Date bookDate;
    private Integer startTime;
    private Integer endTime;
}
