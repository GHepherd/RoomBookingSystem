package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.UserBalanceDto;
import com.scau.entity.dto.UserDto;
import com.scau.entity.vo.UserGetVo;
import com.scau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @GetMapping("/info")
    public ResponseResult<UserGetVo> getUser(){
        return ResponseResult.successResult(userService.getUser());
    }

    @PutMapping("/update")
    public ResponseResult updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return ResponseResult.successResult();
    }
    @PutMapping("/recharge")
    public ResponseResult userRecharge(@RequestBody UserBalanceDto userBalanceDto){
        userService.rechargeUser(userBalanceDto);
        return ResponseResult.successResult();
    }
}
