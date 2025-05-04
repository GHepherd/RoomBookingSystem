package com.scau.entity.vo;


import com.scau.entity.pojo.OrderCancellation;
import lombok.Data;

import java.util.List;

@Data
public class OrderCancellationVO {

    private Long total;

    private List<OrderCancellation> list;


}
