package com.scau.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 会议室表
 * @TableName room
 */
@TableName(value ="room")
@Data
@Builder
public class Room {
    /**
     * 会议室ID
     */
    @TableId(type = IdType.AUTO)
    private Long roomId;

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
     * 开始时间
     */
    private Integer startTime;

    /**
     * 结束时间
     */
    private Integer endTime;

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


}