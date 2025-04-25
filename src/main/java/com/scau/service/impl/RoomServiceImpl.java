package com.scau.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.dto.RoomCreateDto;
import com.scau.entity.pojo.Room;
import com.scau.entity.vo.BookingRoomPageVo;
import com.scau.entity.vo.BookingRoomVo;
import com.scau.exception.UserNotLoginException;
import com.scau.service.RoomService;
import com.scau.mapper.RoomMapper;
import com.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null) {
            throw new UserNotLoginException();
        }
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


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据条件获取会议室列表
     * @param bookingRoomPageDto
     * @return
     */
    @Override
    public BookingRoomPageVo getBookingRooms(BookingRoomPageDto bookingRoomPageDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if(userId == null){
            throw new UserNotLoginException();
        }
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(bookingRoomPageDto.getCapacity()!=null,"capacity",bookingRoomPageDto.getCapacity());
        queryWrapper.eq(bookingRoomPageDto.getHasProjector()!=null,"has_projector",bookingRoomPageDto.getHasProjector());
        queryWrapper.eq(bookingRoomPageDto.getHasSound()!=null,"has_sound",bookingRoomPageDto.getHasSound());
        queryWrapper.eq(bookingRoomPageDto.getHasNetwork()!=null,"has_network",bookingRoomPageDto.getHasNetwork());
        queryWrapper.eq(bookingRoomPageDto.getType()!=null&& !bookingRoomPageDto.getType().isEmpty(),"type",bookingRoomPageDto.getType());
        queryWrapper.like(bookingRoomPageDto.getKeyword()!=null&&bookingRoomPageDto.getKeyword().isEmpty(),"name",bookingRoomPageDto.getKeyword());
        queryWrapper.ne("status",4);
        queryWrapper.orderByAsc("room_id");
        List<Room> rooms = roomMapper.selectList(queryWrapper);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(bookingRoomPageDto.getBookDay());
        Integer startTime = bookingRoomPageDto.getStartTime();
        Integer endTime = bookingRoomPageDto.getEndTime();
        List<BookingRoomVo> bookingRoomVos = new ArrayList<>();
        for (Room room : rooms) {
            String key = RedisConstant.BOOKING_ROOM_LOCK+date+":"+room.getRoomId().toString();
            List<Boolean> list = new ArrayList<>();
            for (int i =startTime;i<=endTime;i++){
                list.add(redisTemplate.opsForValue().getBit(key,i));
            }
            if(list.stream().anyMatch(a->a))continue;
            BookingRoomVo bookingRoomVo=BookingRoomVo.builder()
                    .roomId(room.getRoomId())
                    .name(room.getName())
                    .type(room.getType())
                    .capacity(room.getCapacity())
                    .hasProjector(room.getHasProjector())
                    .hasSound(room.getHasSound())
                    .price(room.getPrice().multiply(BigDecimal.valueOf(endTime-startTime)))
                    .description(room.getDescription())
                    .area(room.getArea())
                    .status(room.getStatus())
                    .bookDate(bookingRoomPageDto.getBookDay())
                    .startTime(startTime)
                    .endTime(endTime).build();
            bookingRoomVos.add(bookingRoomVo);
        }
        BookingRoomPageVo bookingRoomPageVo=new BookingRoomPageVo();
        bookingRoomPageVo.setTotal(bookingRoomVos.size());
        Integer page = bookingRoomPageDto.getPage();
        Integer pageSize = bookingRoomPageDto.getPageSize();
        int startIndex = (page-1)*pageSize;
        int endIndex = page * pageSize;
        bookingRoomPageVo.setBookingRoomVos(bookingRoomVos.subList(startIndex, endIndex));
        return bookingRoomPageVo;
    }


}




