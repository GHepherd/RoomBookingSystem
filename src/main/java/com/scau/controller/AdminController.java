package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.entity.dto.RoomCreateDto;
import com.scau.entity.pojo.Room;
import com.scau.service.RoomService;
import com.scau.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scau/admin")
public class AdminController {
    @Autowired
    private RoomService roomService;
    @PostMapping("/room")
    public ResponseResult createRoom(@RequestBody RoomCreateDto roomCreateDto){
        roomService.createRoom(roomCreateDto);
        return ResponseResult.successResult();
    }
    @PutMapping("rooms/{roomId}")
    public ResponseResult
}
