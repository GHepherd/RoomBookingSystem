package com.scau.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.entity.Cancellation;
import com.scau.service.CancellationService;
import com.scau.mapper.CancellationMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【cancellation(取消预订申请表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class CancellationServiceImpl extends ServiceImpl<CancellationMapper, Cancellation>
    implements CancellationService{

}




