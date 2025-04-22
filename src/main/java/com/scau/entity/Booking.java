package com.scau.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 预订表
 * @TableName booking
 */
@TableName(value ="booking")
@Data
public class Booking {
    /**
     * 预订ID
     */
    @TableId(type = IdType.AUTO)
    private Integer bookingid;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 会议室ID
     */
    private Integer roomid;

    /**
     * 预订日期
     */
    private Date date;

    /**
     * 开始小时(8-21)
     */
    private Integer startHour;

    /**
     * 结束小时(8-21)
     */
    private Integer endHour;

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
        Booking other = (Booking) that;
        return (this.getBookingid() == null ? other.getBookingid() == null : this.getBookingid().equals(other.getBookingid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getRoomid() == null ? other.getRoomid() == null : this.getRoomid().equals(other.getRoomid()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getStartHour() == null ? other.getStartHour() == null : this.getStartHour().equals(other.getStartHour()))
            && (this.getEndHour() == null ? other.getEndHour() == null : this.getEndHour().equals(other.getEndHour()))
            && (this.getTotalAmount() == null ? other.getTotalAmount() == null : this.getTotalAmount().equals(other.getTotalAmount()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBookingid() == null) ? 0 : getBookingid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getRoomid() == null) ? 0 : getRoomid().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getStartHour() == null) ? 0 : getStartHour().hashCode());
        result = prime * result + ((getEndHour() == null) ? 0 : getEndHour().hashCode());
        result = prime * result + ((getTotalAmount() == null) ? 0 : getTotalAmount().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bookingid=").append(bookingid);
        sb.append(", userid=").append(userid);
        sb.append(", roomid=").append(roomid);
        sb.append(", date=").append(date);
        sb.append(", startHour=").append(startHour);
        sb.append(", endHour=").append(endHour);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}