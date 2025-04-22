package com.scau.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 会议室表
 * @TableName room
 */
@TableName(value ="room")
@Data
public class Room {
    /**
     * 会议室ID
     */
    @TableId(type = IdType.AUTO)
    private Integer roomid;

    /**
     * 会议室名称
     */
    private String name;

    /**
     * 类型(classroom-教室型,round-圆桌型)
     */
    private String type;

    /**
     * 座位数
     */
    private Integer capacity;

    /**
     * 面积(平方米)
     */
    private BigDecimal area;

    /**
     * 是否有投影仪(0-无,1-有)
     */
    private Integer hasProjector;

    /**
     * 是否有音响(0-无,1-有)
     */
    private Integer hasSound;

    /**
     * 是否有网络(0-无,1-有)
     */
    private Integer hasNetwork;

    /**
     * 每小时价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态(free-空闲,occupied-使用中,maintenance-维护)
     */
    private Integer status;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    private Integer isdeleted;

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
        Room other = (Room) that;
        return (this.getRoomid() == null ? other.getRoomid() == null : this.getRoomid().equals(other.getRoomid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCapacity() == null ? other.getCapacity() == null : this.getCapacity().equals(other.getCapacity()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getHasProjector() == null ? other.getHasProjector() == null : this.getHasProjector().equals(other.getHasProjector()))
            && (this.getHasSound() == null ? other.getHasSound() == null : this.getHasSound().equals(other.getHasSound()))
            && (this.getHasNetwork() == null ? other.getHasNetwork() == null : this.getHasNetwork().equals(other.getHasNetwork()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsdeleted() == null ? other.getIsdeleted() == null : this.getIsdeleted().equals(other.getIsdeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoomid() == null) ? 0 : getRoomid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCapacity() == null) ? 0 : getCapacity().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getHasProjector() == null) ? 0 : getHasProjector().hashCode());
        result = prime * result + ((getHasSound() == null) ? 0 : getHasSound().hashCode());
        result = prime * result + ((getHasNetwork() == null) ? 0 : getHasNetwork().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsdeleted() == null) ? 0 : getIsdeleted().hashCode());
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
        sb.append(", roomid=").append(roomid);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", capacity=").append(capacity);
        sb.append(", area=").append(area);
        sb.append(", hasProjector=").append(hasProjector);
        sb.append(", hasSound=").append(hasSound);
        sb.append(", hasNetwork=").append(hasNetwork);
        sb.append(", price=").append(price);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", isdeleted=").append(isdeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}