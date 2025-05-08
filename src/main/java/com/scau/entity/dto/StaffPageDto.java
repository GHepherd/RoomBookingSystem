package com.scau.entity.dto;

import lombok.Data;

/**
 * @author <a href="https://github.com/TennKane">gtkkang</a>
 */
@Data
public class StaffPageDto {
    private Integer page;
    private Integer pageSize;
    private Integer status;
    private String keyword;
}
