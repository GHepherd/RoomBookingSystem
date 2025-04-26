package com.scau.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.entity.dto.BookingRoomPageDto;
import com.scau.entity.dto.SubmitBookingRoomDto;
import com.scau.entity.pojo.Booking;
import com.scau.entity.pojo.Order;
import com.scau.entity.pojo.Room;
import com.scau.entity.vo.SuccessBookingPageVo;
import com.scau.entity.vo.SuccessBookingVo;
import com.scau.exception.RoomHasBeenBookedException;
import com.scau.exception.SubmitBookingErrorException;
import com.scau.exception.UserNotLoginException;
import com.scau.mapper.OrderMapper;
import com.scau.mapper.RoomMapper;
import com.scau.service.BookingService;
import com.scau.mapper.BookingMapper;
import com.scau.service.RoomService;
import com.scau.utils.DelayMsgUtil;
import com.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author ASUS
* @description 针对表【booking(预订表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking>
    implements BookingService{

    private static final Object submitLock = new Object();

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DelayMsgUtil delayMsgUtil;

    /**
     * 提交预约请求
     * @param roomId
     * @param submitBookingRoomDto
     * @return
     */
    @Override
    public void submitBookingRoom(Long roomId, SubmitBookingRoomDto submitBookingRoomDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if(userId == null){
            throw new UserNotLoginException();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(submitBookingRoomDto.getBookDay());
        String key = RedisConstant.BOOKING_ROOM_LOCK+date+":"+roomId;
        Integer startTime = submitBookingRoomDto.getStartTime();
        Integer endTime = submitBookingRoomDto.getEndTime();
        //双检，防止出现数据一致性问题
        for (int i=startTime;i<=endTime;i++){
            if(redisTemplate.opsForValue().getBit(key,i)){
                throw new RoomHasBeenBookedException();
            }
        }
        synchronized (submitLock) {
            for (int i=startTime;i<=endTime;i++){
                if(redisTemplate.opsForValue().getBit(key,i)){
                    throw new RoomHasBeenBookedException();
                }
            }
            try {
                for (int i=startTime;i<=endTime;i++){
                    redisTemplate.opsForValue().setBit(key,i,true);
                }
                QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("room_id",roomId);
                Room room = roomMapper.selectOne(queryWrapper);
                Order order = Order.builder()
                        .userId(userId)
                        .roomId(roomId)
                        .date(submitBookingRoomDto.getBookDay())
                        .startTime(startTime)
                        .endTime(endTime)
                        .totalAmount(room.getPrice().multiply(BigDecimal.valueOf(endTime-startTime)))
                        .status(0)
                        .createTime(new Date())
                        .updateTime(new Date()).build();
                orderMapper.insert(order);
                delayMsgUtil.sendDelayMsg(RedisConstant.ORDER_BLOCKING_QUEUE,order.getOrderId().toString(),RedisConstant.ORDER_TIMEOUT);
                }
            catch (Exception e){
                //异常兜底策略，释放redis中的锁
                for (int i=startTime;i<=endTime;i++){
                    redisTemplate.opsForValue().setBit(key,i,false);
                }
                throw new SubmitBookingErrorException();
            }
        }
    }

    /**
     * 获取我的成功预订列表
     * @param bookingRoomPageDto
     * @return
     */
    @Override
    public SuccessBookingPageVo getBookings(BookingRoomPageDto bookingRoomPageDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if(userId == null){
            throw new UserNotLoginException();
        }
        SuccessBookingPageVo successBookingPageVo = new SuccessBookingPageVo();
        Page<Booking> bookingPage = new Page<>();
        Page<Booking> page = new Page<>(bookingRoomPageDto.getPage(),bookingPage.getSize());
        QueryWrapper<Booking> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("status",0);
        queryWrapper.orderByDesc("create_time");
        page = bookingMapper.selectPage(page, queryWrapper);
        List<Booking> records = page.getRecords();
        successBookingPageVo.setTotal((int) page.getTotal());
        List<SuccessBookingVo> successBookingVos = records.stream().map(o -> {
            Room room = roomMapper.selectById(o.getRoomId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return SuccessBookingVo.builder()
                    .bookingId(o.getBookingId())
                    .roomId(o.getRoomId())
                    .roomName(room.getName())
                    .date(sdf.format(o.getDate()))
                    .startTime(o.getStartTime())
                    .endTime(o.getEndTime())
                    .totalAmount(o.getTotalAmount())
                    .status(o.getStatus()).build();
        }).toList();
        successBookingPageVo.setSuccessBookingVos(successBookingVos);
        return successBookingPageVo;
    }
}




