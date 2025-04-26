package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.OrderPageDto;
import com.scau.entity.vo.OrderPageVo;
import com.scau.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scau/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 支付订单
     * @param orderId
     * @return
     */
    @PostMapping("/{orderId}")
    public ResponseResult payOrder(@PathVariable Long orderId) {
        return orderService.payOrder(orderId);
    }

    /**
     * 且取消未支付订单
     * @param orderId
     * @return
     */
    @DeleteMapping("/{orderId}")
    public ResponseResult cancelOrder(@PathVariable Long orderId) {
        orderService.orderCancel(orderId);
        return ResponseResult.successResult("取消成功");
    }

    /**
     * 获取订单列表
     * @param orderPageDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<OrderPageVo> getOrderPage(@RequestBody OrderPageDto orderPageDto) {
        OrderPageVo orderPageVo = orderService.getOrderPage(orderPageDto);
        return ResponseResult.successResult(orderPageVo);
    }

}
