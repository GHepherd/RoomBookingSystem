package com.scau.controller;

import com.scau.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/scau/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
}
