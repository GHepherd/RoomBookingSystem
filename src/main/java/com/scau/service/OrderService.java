package com.scau.service;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.OrderPageDto;
import com.scau.entity.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scau.entity.vo.OrderPageVo;

/**
* @author ASUS
* @description 针对表【order(支付记录表)】的数据库操作Service
* @createDate 2025-04-23 00:20:53
*/
public interface OrderService extends IService<Order> {

    void orderCancel(Long orderId);

    void delayOrderCancel(Long orderId);

    ResponseResult payOrder(Long orderId);

    OrderPageVo getOrderPage(OrderPageDto orderPageDto);
}
