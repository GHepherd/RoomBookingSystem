package com.scau.entity;

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
    private Integer cancellationid;

    /**
     * 预订ID
     */
    private Integer bookingId;

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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Cancellation other = (Cancellation) that;
        return (this.getCancellationid() == null ? other.getCancellationid() == null : this.getCancellationid().equals(other.getCancellationid()))
            && (this.getBookingId() == null ? other.getBookingId() == null : this.getBookingId().equals(other.getBookingId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
            && (this.getProcessorId() == null ? other.getProcessorId() == null : this.getProcessorId().equals(other.getProcessorId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCancellationid() == null) ? 0 : getCancellationid().hashCode());
        result = prime * result + ((getBookingId() == null) ? 0 : getBookingId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getProcessorId() == null) ? 0 : getProcessorId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cancellationid=").append(cancellationid);
        sb.append(", bookingId=").append(bookingId);
        sb.append(", status=").append(status);
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", processorId=").append(processorId);
        sb.append("]");
        return sb.toString();
    }
}