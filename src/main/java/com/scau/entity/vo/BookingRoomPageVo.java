package com.scau.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BookingRoomPageVo {
    private int total;
    private List<BookingRoomVo> bookingRoomVos;
}