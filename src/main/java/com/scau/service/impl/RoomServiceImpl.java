package com.scau.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.entity.pojo.Room;
import com.scau.service.RoomService;
import com.scau.mapper.RoomMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【room(会议室表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
    implements RoomService{

}




