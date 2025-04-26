package com.scau.entity.dto;

import lombok.Data;

import java.util.Date;
@Data
public class SubmitBookingRoomDto {
    private Date bookDay;
    private Integer startTime;
    private Integer endTime;
}
