package com.scau.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.entity.dto.RoomCreateDto;
import com.scau.entity.pojo.Room;
import com.scau.service.RoomService;
import com.scau.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author ASUS
* @description 针对表【room(会议室表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
    implements RoomService{
    @Autowired
    private RoomMapper roomMapper;
    @Override
    public void createRoom(RoomCreateDto roomCreateDto) {
        if ()
        Room room = Room.builder()
                .name(roomCreateDto.getName())
                .type(roomCreateDto.getType())
                .capacity(roomCreateDto.getCapacity())
                .hasProjector(roomCreateDto.getHasProjector())
                .hasSound(roomCreateDto.getHasSound())
                .hasNetwork(roomCreateDto.getHasNetwork())
                .price(roomCreateDto.getPrice())
                .description(roomCreateDto.getDescription())
                .area(roomCreateDto.getArea())
                .status(roomCreateDto.getStatus())
                .startTime(roomCreateDto.getStartTime())
                .endTime(roomCreateDto.getEndTime())
                .isdeleted(0)
                .createTime(new Date())
                .updateTime(new Date()).build();
        roomMapper.insert(room);
    }
}




