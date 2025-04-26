package com.scau.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderPageVo {
    Integer total;
    List<OrderVo> orders;
}
