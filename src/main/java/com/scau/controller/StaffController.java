package com.scau.controller;

import com.scau.entity.ResponseResult;
import com.scau.mapper.CancellationMapper;
import com.scau.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scau/staff")
public class StaffController {

    @Autowired
    private CancellationService cancellationService;

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
}
