package com.scau.controller;

import com.scau.constant.ResponseConstant;
import com.scau.entity.ResponseResult;
import com.scau.entity.dto.OrderPageDto;
import com.scau.entity.dto.RoomDto;
import com.scau.entity.vo.AllRoomStatusVO;
import com.scau.entity.vo.OrderCancellationVO;
import com.scau.mapper.CancellationMapper;
import com.scau.service.CancellationService;
import com.scau.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("scau/staff")
public class StaffController {

    @Autowired
    private CancellationService cancellationService;

    @Autowired
    private RoomService roomService;

    /**
     * 处理退订申请
     * @param cancellationId
     * @return
     */
    @PostMapping("/cancellations/{cancellationId}/process")
    public ResponseResult process(@PathVariable("cancellationId") Long cancellationId) {
        cancellationService.process(cancellationId);
        return ResponseResult.successResult();
    }

    @PutMapping("/rooms/{roomId}/status")
    public ResponseResult updateStatus(@PathVariable Long roomId, RoomDto roomDto){
        roomService.updateStatus(roomId,roomDto);
        return ResponseResult.successResult(ResponseConstant.UPDATE_SUCCESS);
    }

    @GetMapping("/rooms/status")
    public ResponseResult<List<AllRoomStatusVO>> getAllRoomStatus(){
        List<AllRoomStatusVO> result=roomService.getAllRoomStatus();
        return ResponseResult.successResult(result);
    }

    @GetMapping("/cancellation")
    public ResponseResult<OrderCancellationVO> getCancellations(@RequestBody OrderPageDto orderPageDto){
        OrderCancellationVO result=cancellationService.getCancellation(orderPageDto);
        return ResponseResult.successResult(result);
    }
}
