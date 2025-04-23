package com.scau.controller;

import com.scau.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scau/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
}
