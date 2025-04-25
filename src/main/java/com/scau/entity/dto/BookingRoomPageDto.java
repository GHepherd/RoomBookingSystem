package com.scau.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookingRoomPageDto {
    int page;
    int pageSize;
    Date bookDay;
    int startTime;
    int endTime;
    int capacity;
    int hasProjector;
    int hasSound;
    int hasNetwork;
    String type;
    String keyword;
}
