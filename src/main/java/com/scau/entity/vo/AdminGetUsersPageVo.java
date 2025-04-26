package com.scau.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class AdminGetUsersPageVo {
        private Integer total;
        private List<AdminGetUsersVo> list;
}
