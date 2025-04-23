package com.scau.entity.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVo {
    private String token;
    private int status;
}
