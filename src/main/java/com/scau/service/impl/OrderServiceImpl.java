package com.scau.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.entity.ResponseResult;
import com.scau.entity.dto.OrderPageDto;
import com.scau.entity.pojo.Booking;
import com.scau.entity.pojo.Order;
import com.scau.entity.pojo.User;
import com.scau.entity.vo.OrderPageVo;
import com.scau.entity.vo.OrderVo;
import com.scau.exception.BaseException;
import com.scau.exception.OrderNotExistException;
import com.scau.exception.UserNotExistException;
import com.scau.exception.UserNotLoginException;
import com.scau.mapper.BookingMapper;
import com.scau.mapper.UserMapper;
import com.scau.service.BookingService;
import com.scau.service.OrderService;
import com.scau.mapper.OrderMapper;
import com.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* @author ASUS
* @description 针对表【order(支付记录表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 关闭订单
     * @param orderId
     */
    @Override
    public void orderCancel(Long orderId) {
        //更新订单状态
        Order order = orderMapper.selectById(orderId);
        if(order==null){
            throw new OrderNotExistException();
        }
        if(order.getStatus()!=0){
            return;
        }
        order.setStatus(3);
        order.setUpdateTime(new Date());
        orderMapper.updateById(order);
        Integer startTime = order.getStartTime();
        Integer endTime = order.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(order.getDate());
        String key = RedisConstant.BOOKING_ROOM_LOCK+date+":"+order.getRoomId();
        for(int i=startTime;i<=endTime;i++){
            //释放redis中的锁
            redisTemplate.opsForValue().setBit(key,i,false);
        }

    }

    /**
     * 支付订单
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult payOrder(Long orderId) {
        Long userId = ThreadLocalUtil.getUserId();
        if(userId==null){
            throw new UserNotLoginException();
        }
        User user = userMapper.selectById(userId);
        if(user==null){
            throw new UserNotExistException();
        }
        BigDecimal balance = user.getBalance();
        Order order = orderMapper.selectById(orderId);
        if(order==null){
            throw new OrderNotExistException();
        }
        if(order.getStatus()!=0){
            throw new BaseException("订单已支付或已取消");
        }
        BigDecimal totalAmount = order.getTotalAmount();
        if(balance.compareTo(totalAmount)<0){
            return ResponseResult.successResult("支付失败");
        }
        user.setBalance(balance.subtract(totalAmount));
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        order.setStatus(1);
        order.setUpdateTime(new Date());
        orderMapper.updateById(order);
        Booking booking = Booking.builder()
                .userId(userId)
                .orderId(orderId)
                .roomId(order.getRoomId())
                .date(order.getDate())
                .startTime(order.getStartTime())
                .endTime(order.getEndTime())
                .totalAmount(totalAmount)
                .status(0)
                .createTime(new Date())
                .updateTime(new Date()).build();
        bookingMapper.insert(booking);
        return ResponseResult.successResult("支付成功");
    }

    /**
     * 获取订单列表
     * @param orderPageDto
     * @return
     */
    @Override
    public OrderPageVo getOrderPage(OrderPageDto orderPageDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if(userId==null){
            throw new UserNotLoginException();
        }
        Page<Order> page=new Page<>(orderPageDto.getPage(),orderPageDto.getPageSize());
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("create_time");
        page=orderMapper.selectPage(page,queryWrapper);
        OrderPageVo orderPageVo=new OrderPageVo();
        orderPageVo.setTotal((int) page.getTotal());
        List<Order> orders = page.getRecords();

        List<OrderVo> orderVos = orders.stream().map(o -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return OrderVo.builder()
                    .orderId(o.getOrderId())
                    .roomId(o.getRoomId())
                    .date(sdf.format(o.getDate()))
                    .startTime(o.getStartTime())
                    .endTime(o.getEndTime())
                    .totalAmount(o.getTotalAmount())
                    .status(o.getStatus()).build();
        }).toList();
        orderPageVo.setOrders(orderVos);
        return orderPageVo;
    }
}




