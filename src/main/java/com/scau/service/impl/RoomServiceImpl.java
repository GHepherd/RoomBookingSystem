package com.scau.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.dto.RoomDto;
import com.scau.entity.dto.RoomPageDto;
import com.scau.entity.pojo.Room;
import com.scau.entity.vo.AdminRoomVo;
import com.scau.entity.vo.BookingRoomPageVo;
import com.scau.entity.vo.BookingRoomVo;
import com.scau.entity.vo.AdminRoomPageVo;
import com.scau.exception.UserNotLoginException;
import com.scau.mapper.UserMapper;
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
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Override
    public void createRoom(RoomDto roomDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        Room room = Room.builder()
                .name(roomDto.getName())
                .type(roomDto.getType())
                .capacity(roomDto.getCapacity())
                .hasProjector(roomDto.getHasProjector())
                .hasSound(roomDto.getHasSound())
                .hasNetwork(roomDto.getHasNetwork())
                .price(roomDto.getPrice())
                .description(roomDto.getDescription())
                .area(roomDto.getArea())
                .status(roomDto.getStatus())
                .startTime(roomDto.getStartTime())
                .endTime(roomDto.getEndTime())
                .isDeleted(0)
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

    /**
     * 更新会议室
     * @param roomDto
     * @param roomId
     */
    @Override
    public void updateRoom(RoomDto roomDto, Long roomId) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        Room room = Room.builder()
                .name(roomDto.getName())
                .type(roomDto.getType())
                .capacity(roomDto.getCapacity())
                .hasProjector(roomDto.getHasProjector())
                .hasSound(roomDto.getHasSound())
                .hasNetwork(roomDto.getHasNetwork())
                .price(roomDto.getPrice())
                .description(roomDto.getDescription())
                .area(roomDto.getArea())
                .status(roomDto.getStatus())
                .startTime(roomDto.getStartTime())
                .endTime(roomDto.getEndTime())
                .isDeleted(0)
                .createTime(new Date())
                .updateTime(new Date()).build();
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("room_id",roomId);
        roomMapper.update(room,updateWrapper);
    }

    /**
     * 删除会议室
     * @param roomId
     */
    @Override
    public void deleteRoom(Long roomId) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        roomMapper.deleteById(roomId);
    }

    /**
     * 会议室分页查询（管理员）
     * @param roomPageDto
     * @return
     */
    @Override
    public AdminRoomPageVo getRooms(RoomPageDto roomPageDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        AdminRoomPageVo adminRoomPageVo = new AdminRoomPageVo();
        Page<Room> page = new Page<>(roomPageDto.getPage(),roomPageDto.getPageSize());
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        if (roomPageDto.getKeyword() != null){
            queryWrapper.like("name",roomPageDto.getKeyword());
        }
        Page<Room> roomPage = roomMapper.selectPage(page,queryWrapper);
        List<AdminRoomVo> list = roomPage.getRecords().stream()
                .map(room -> {
                    AdminRoomVo adminRoomVo = new AdminRoomVo();
                    adminRoomVo.setRoomId(room.getRoomId());
                    adminRoomVo.setName(room.getName());
                    adminRoomVo.setType(room.getType());
                    adminRoomVo.setCapacity(room.getCapacity());
                    adminRoomVo.setHasProjector(room.getHasProjector());
                    adminRoomVo.setHasSound(room.getHasSound());
                    adminRoomVo.setHasNetwork(room.getHasNetwork());
                    adminRoomVo.setPrice(room.getPrice());
                    adminRoomVo.setDescription(room.getDescription());
                    adminRoomVo.setArea(room.getArea());
                    adminRoomVo.setStatus(room.getStatus());
                    adminRoomVo.setStartTime(room.getStartTime());
                    adminRoomVo.setEndTime(room.getEndTime());
                    return adminRoomVo;
        }).collect(Collectors.toList());
        adminRoomPageVo.setList(list);
        adminRoomPageVo.setTotal(list.size());
        return adminRoomPageVo;
    }
}




