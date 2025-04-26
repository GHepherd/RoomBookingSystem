package com.scau.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 支付记录表
 * @TableName order
 */
@TableName(value ="orders")
@Data
@Builder
public class Order {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;


    /**
     * 用户ID
     */
    private Long userId;

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
     * 支付金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态(0-未支付,1-已支付,2-已退款)
     */
    private Integer status;

    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}