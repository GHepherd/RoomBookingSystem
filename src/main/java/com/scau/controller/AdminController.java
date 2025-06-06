package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.*;
import com.scau.entity.vo.AdminGetStaffsPageVo;
import com.scau.entity.vo.AdminRoomPageVo;
import com.scau.entity.vo.AdminGetUsersPageVo;
import com.scau.service.RoomService;
import com.scau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scau/admin")
public class AdminController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @PostMapping("/room")
    public ResponseResult createRoom(@RequestBody RoomDto roomDto){
        roomService.createRoom(roomDto);
        return ResponseResult.successResult();
    }
    @PutMapping("/rooms/{roomId}")
    public ResponseResult updateRoom(@RequestBody RoomDto roomDto,@PathVariable Long roomId){
        roomService.updateRoom(roomDto,roomId);
        return ResponseResult.successResult();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseResult deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return ResponseResult.successResult();
    }

    @PostMapping("/staff")
    public ResponseResult createStaff(@RequestBody UserDto userDto){
        userService.createStaff(userDto);
        return ResponseResult.successResult();
    }

    @PutMapping("/users/{userId}/{status}")
    public ResponseResult updateUserStatus(@PathVariable Long userId,@PathVariable Integer status){
        userService.updateUserStatus(userId,status);
        return ResponseResult.successResult();
    }

    @PostMapping("/users")
    public ResponseResult<AdminGetUsersPageVo> getUsers(@RequestBody UserPageDto userPageDto){
        return ResponseResult.successResult(userService.getUsers(userPageDto));
    }

    @PostMapping("/staffs")
    public ResponseResult<AdminGetStaffsPageVo> getStaffs(@RequestBody StaffPageDto staffPageDto){
        return ResponseResult.successResult(userService.getStaffs(staffPageDto));
    }

    @PostMapping("/rooms")
    public ResponseResult<AdminRoomPageVo> getRooms(@RequestBody RoomPageDto roomPageDto){
        return ResponseResult.successResult(roomService.getRooms(roomPageDto));
    }
}
