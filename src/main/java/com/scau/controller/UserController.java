package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.UserDto;
import com.scau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scau/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseResult.successResult();
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDto userDto) {
        return ResponseResult.successResult(userService.login(userDto));
    }
}
