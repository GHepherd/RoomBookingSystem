package com.scau.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.entity.Order;
import com.scau.service.OrderService;
import com.scau.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【order(支付记录表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




