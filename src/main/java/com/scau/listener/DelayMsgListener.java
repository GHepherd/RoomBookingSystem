package com.scau.listener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.scau.constant.RedisConstant;
import com.scau.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class DelayMsgListener {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void OrderCancel(){
        log.info("开启线程监听延迟消息：");
        RBlockingQueue<String> queue = redissonClient.getBlockingQueue(RedisConstant.ORDER_BLOCKING_QUEUE);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(()->{
            while(true){
                String take=null;
                try {
                    take = queue.take();
                }
                catch (InterruptedException e) {
                    throw new RuntimeException();
                }
                if(StringUtils.isNotBlank(take)){
                    log.info("监听到延迟关单消息：{}", take);
                    orderService.orderCancel(Long.valueOf(take));
                }
            }
        });
    }
}
