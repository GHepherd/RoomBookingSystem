package com.scau.service;

import com.scau.entity.dto.OrderPageDto;
import com.scau.entity.pojo.Cancellation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scau.entity.vo.OrderCancellationVO;

/**
* @author ASUS
* @description 针对表【cancellation(取消预订申请表)】的数据库操作Service
* @createDate 2025-04-23 00:20:53
*/
public interface CancellationService extends IService<Cancellation> {

    void process(Long cancellationId);

    OrderCancellationVO getCancellation(OrderPageDto orderPageDto);
}
