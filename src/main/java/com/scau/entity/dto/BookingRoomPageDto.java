package com.scau.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookingRoomPageDto {
    Integer page;
    Integer pageSize;
    Date bookDay;
    Integer startTime;
    Integer endTime;
    Integer capacity;
    Integer hasProjector;
    Integer hasSound;
    Integer hasNetwork;
    String type;
    String keyword;
}
