package com.scau.controller;

import com.scau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/scau/users")
public class UserController {
    @Autowired
    private UserService userService;
}
