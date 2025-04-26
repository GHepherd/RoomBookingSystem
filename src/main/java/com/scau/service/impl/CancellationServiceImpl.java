package com.scau.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.constant.ResponseConstant;
import com.scau.entity.pojo.Booking;
import com.scau.entity.pojo.Cancellation;
import com.scau.entity.pojo.Order;
import com.scau.entity.pojo.User;
import com.scau.exception.BaseException;
import com.scau.exception.UserNotLoginException;
import com.scau.mapper.BookingMapper;
import com.scau.mapper.OrderMapper;
import com.scau.mapper.UserMapper;
import com.scau.service.CancellationService;
import com.scau.mapper.CancellationMapper;
import com.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author ASUS
* @description 针对表【cancellation(取消预订申请表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class CancellationServiceImpl extends ServiceImpl<CancellationMapper, Cancellation>
    implements CancellationService{

    @Autowired
    private CancellationMapper cancellationMapper;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void process(Long cancellationId) {
        Long userId = ThreadLocalUtil.getUserId();
        User user = userMapper.selectById(userId);
        if(!user.getRole().equals("staff")){
            throw new BaseException(ResponseConstant.NO_OPERATOR_AUTH);
        }
        if(userId == null){
            throw new UserNotLoginException();
        }
        Cancellation cancellation = cancellationMapper.selectById(cancellationId);
        Long bookingId = cancellation.getBookingId();
        Booking booking = bookingMapper.selectById(bookingId);
        Long orderId = booking.getOrderId();
        Order order = orderMapper.selectById(orderId);
        Integer startTime = order.getStartTime();
        Integer endTime = order.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(order.getDate());
        String key = RedisConstant.BOOKING_ROOM_LOCK+date+":"+order.getRoomId();
        user = userMapper.selectById(order.getUserId());
        try {
            user.setBalance(user.getBalance().add(cancellation.getRefundAmount()));
            user.setUpdateTime(new Date());
            userMapper.updateById(user);
            cancellation.setStatus(1);
            cancellation.setProcessorId(Math.toIntExact(userId));
            cancellationMapper.updateById(cancellation);
            bookingMapper.deleteById(bookingId);
            order.setStatus(2);
            order.setUpdateTime(new Date());
            orderMapper.updateById(order);
            for (int i=startTime;i<=endTime;i++){
                redisTemplate.opsForValue().setBit(key,i,false);
            }
        } catch (Exception e) {
            for (int i=startTime;i<=endTime;i++){
                redisTemplate.opsForValue().setBit(key,i,true);
            }
            throw new BaseException("发生未知错误，请重试");
        }
    }
}




