package com.scau.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.entity.pojo.Booking;
import com.scau.service.BookingService;
import com.scau.mapper.BookingMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【booking(预订表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking>
    implements BookingService{

}




