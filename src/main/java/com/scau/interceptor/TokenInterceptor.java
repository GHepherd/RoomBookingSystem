package com.scau.interceptor;

import com.scau.constant.RedisConstant;
import com.scau.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
@Log4j2
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 请求前将userId存入到当前线程中
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @return 是否通过
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String key= RedisConstant.LOGIN+token;
        Long userId = (Long) redisTemplate.opsForValue().get(key);
        if(userId != null){
            //存入到当前线程中
            ThreadLocalUtil.setUserId(userId);
        }
        return true;
    }

    /**
     * 请求结束后清除当前线程中的userId
     * @param request 请求
     * @param response  响应
     * @param handler 处理器
     * @param ex 异常
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.clear();
    }

}
