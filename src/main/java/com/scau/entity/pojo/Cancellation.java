package com.scau.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 取消预订申请表
 * @TableName cancellation
 */
@TableName(value ="cancellation")
@Data
public class Cancellation {
    /**
     * 取消ID
     */
    @TableId(type = IdType.AUTO)
    private Long cancellationId;

    /**
     * 预订ID
     */
    private Long bookingId;

    /**
     * 状态(0-待处理,1-已同意)
     */
    private Integer status;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 处理人ID
     */
    private Integer processorId;


}