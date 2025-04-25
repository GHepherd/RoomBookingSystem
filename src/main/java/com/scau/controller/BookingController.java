package com.scau.controller;

import com.scau.entity.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scau/bookings")
public class BookingController {

    @GetMapping("/rooms")
    public ResponseResult getBookingRooms() {
        return ResponseResult.successResult();
    }
}
