package com.scau.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 预订表
 * @TableName booking
 */
@TableName(value ="booking")
@Data
@Builder
public class Booking {
    /**
     * 预订ID
     */
    @TableId(type = IdType.AUTO)
    private Long bookingId;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 会议室ID
     */
    private Long roomId;

    /**
     * 预订日期
     */
    private Date date;

    /**
     * 开始小时(8-21)
     */
    private Integer startTime;

    /**
     * 结束小时(8-21)
     */
    private Integer endTime;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态(toBeReviewd-待审核)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}