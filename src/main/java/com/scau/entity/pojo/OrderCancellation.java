package com.scau.entity.pojo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderCancellation {

    private Long cancellationId;

    private Long bookingId;

    private Long orderId;

    private Long roomId;

    private Long userId;

    private String userName;

    private String roomName;

    private Integer startTime;

    private Integer endTime;

    private BigDecimal totalAmount;

    private String date;

    private BigDecimal refundAmount;

}
