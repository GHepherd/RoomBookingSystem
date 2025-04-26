package com.scau.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingCancelVo {
    private Long cancellationId;
    private BigDecimal estimatedRefund;
}
