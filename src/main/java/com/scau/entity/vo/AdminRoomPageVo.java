package com.scau.entity.vo;

import lombok.Data;

import java.util.List;
@Data
public class AdminRoomPageVo {
        private Integer total;
        private List<AdminRoomVo> list;
}
