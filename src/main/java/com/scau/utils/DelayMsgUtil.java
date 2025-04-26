package com.scau.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DelayMsgUtil {

    @Autowired
    private RedissonClient redissonClient;

    public void sendDelayMsg(String queueName,String data,int ttl) {
        try {
            RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(queueName);
            RDelayedQueue<String> delayedQueue=redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(data,ttl, TimeUnit.MINUTES);
            log.info("发送延迟消息成功：{}", data);
        } catch (Exception e) {
            log.error("[延迟消息]发送异常：{}", data);
            throw new RuntimeException(e);
        }
    }
}
