package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.vo.BookingRoomPageVo;
import com.scau.service.BookingService;
import com.scau.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scau/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;
    /**
     * 根据条件获取会议室列表
     * @param bookingRoomPageDto
     * @return
     */
    @GetMapping("/rooms")
    public ResponseResult<BookingRoomPageVo> getBookingRooms(@RequestBody BookingRoomPageDto bookingRoomPageDto) {
        BookingRoomPageVo bookingRoomPageVo = roomService.getBookingRooms(bookingRoomPageDto);
        return ResponseResult.successResult(bookingRoomPageVo);
    }
}
