package com.scau.entity.vo;

import lombok.Data;

/**
 * @author <a href="https://github.com/TennKane">gtkkang</a>
 */
@Data
public class AdminGetStaffsVo {
    private Long userId;
    private String username;
    private String name;
    private String phone;
    private Integer status;
    private String createTime;
}
