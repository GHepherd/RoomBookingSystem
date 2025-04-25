package com.scau.service;

import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.dto.RoomDto;
import com.scau.entity.pojo.Room;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scau.entity.vo.BookingRoomPageVo;

/**
* @author ASUS
* @description 针对表【room(会议室表)】的数据库操作Service
* @createDate 2025-04-23 00:20:53
*/
public interface RoomService extends IService<Room> {

    void createRoom(RoomDto roomDto);

    BookingRoomPageVo getBookingRooms(BookingRoomPageDto bookingRoomPageDto);

    void updateRoom(RoomDto roomDto, Long roomId);

    void deleteRoom(Long roomId);
}
