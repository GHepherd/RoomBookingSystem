package com.scau.config;


import com.scau.interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Resource
    private TokenInterceptor tokenInterceptor;

    /**
     * 注册自定义拦截器
     * @param registry  注册器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/scau/**")
                .excludePathPatterns("/scau/user/login","/scau/user/register");
    }
}
