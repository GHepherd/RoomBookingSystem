package com.scau.service;

import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.dto.SubmitBookingRoomDto;
import com.scau.entity.pojo.Booking;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scau.entity.vo.BookingCancelVo;
import com.scau.entity.vo.SuccessBookingPageVo;

/**
* @author ASUS
* @description 针对表【booking(预订表)】的数据库操作Service
* @createDate 2025-04-23 00:20:53
*/
public interface BookingService extends IService<Booking> {

    void submitBookingRoom(Long roomId, SubmitBookingRoomDto submitBookingRoomDto);

    SuccessBookingPageVo getBookings(BookingRoomPageDto bookingRoomPageDto);

    BookingCancelVo cancelBooking(Long bookingId);
}
