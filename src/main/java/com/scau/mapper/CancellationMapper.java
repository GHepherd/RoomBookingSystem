package com.scau.mapper;

import com.scau.entity.pojo.Cancellation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scau.entity.pojo.OrderCancellation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author ASUS
* @description 针对表【cancellation(取消预订申请表)】的数据库操作Mapper
* @createDate 2025-04-23 00:20:53
* @Entity generator.entity.Cancellation
*/
@Mapper
public interface CancellationMapper extends BaseMapper<Cancellation> {

    List<OrderCancellation> getOrderCancellation(Integer page,Integer pageSize);
}




