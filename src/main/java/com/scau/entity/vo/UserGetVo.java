package com.scau.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserGetVo {
    private String username;
    private String name;
    private String phone;
    private String role;
    private String company;
    private Integer status;
    private BigDecimal balance;
    private String createTime;
}
