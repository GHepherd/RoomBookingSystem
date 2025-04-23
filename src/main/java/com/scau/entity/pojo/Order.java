package com.scau.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 支付记录表
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 预订ID
     */
    private Long bookingId;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 支付金额
     */
    private BigDecimal totalamount;

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