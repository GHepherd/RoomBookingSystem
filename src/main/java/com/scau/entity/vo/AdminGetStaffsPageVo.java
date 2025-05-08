package com.scau.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="https://github.com/TennKane">gtkkang</a>
 */
@Data
public class AdminGetStaffsPageVo {
    private Integer total;
    private List<AdminGetStaffsVo> list;
}
