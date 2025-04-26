package com.scau.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class SuccessBookingPageVo {
    private Integer total;
    private List<SuccessBookingVo> successBookingVos;
}
