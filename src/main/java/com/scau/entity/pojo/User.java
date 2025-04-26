package com.scau.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
@Builder
public class User {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码(加密)
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 所属公司(客户)
     */
    private String company;

    /**
     * 角色(admin-管理员,staff-员工,customer-客户)
     */
    private String role;

    /**
     * 状态(0-待审核,1-正常,2-冻结)
     */
    private Integer status;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 是否删除(0-未删除,1-已删除)
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}