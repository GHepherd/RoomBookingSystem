package com.scau.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SuccessBookingVo {
    private Long bookingId;
    private Long roomId;
    private String roomName;
    private String date;
    private Integer startTime;
    private Integer endTime;
    private BigDecimal totalAmount;
    private Integer status;
}
