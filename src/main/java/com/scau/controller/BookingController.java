package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.dto.SubmitBookingRoomDto;
import com.scau.entity.vo.BookingRoomPageVo;
import com.scau.entity.vo.SuccessBookingPageVo;
import com.scau.service.BookingService;
import com.scau.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 提交预约请求
     * @param roomId
     * @param submitBookingRoomDto
     * @return
     */
    @PostMapping("/{roomId}")
    public ResponseResult submitBookingRoom(@PathVariable Long roomId,@RequestBody SubmitBookingRoomDto submitBookingRoomDto) {
        bookingService.submitBookingRoom(roomId,submitBookingRoomDto);
        return ResponseResult.successResult();
    }

    /**
     * 获取我的成功预订列表
     * @param bookingRoomPageDto
     * @return
     */
    @GetMapping("/bookings")
    public ResponseResult<SuccessBookingPageVo> getBookings(@RequestBody BookingRoomPageDto bookingRoomPageDto) {
        return ResponseResult.successResult(bookingService.getBookings(bookingRoomPageDto));
    }
}
