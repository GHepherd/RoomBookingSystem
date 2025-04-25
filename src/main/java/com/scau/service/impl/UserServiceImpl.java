package com.scau.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.entity.dto.UserDto;
import com.scau.entity.pojo.User;
import com.scau.entity.vo.UserLoginVo;
import com.scau.exception.ErrorPasswordException;
import com.scau.exception.UserNotExistException;
import com.scau.service.UserService;
import com.scau.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author ASUS
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void register(UserDto userDto) {
        Assert.notNull(userDto);
        User user = User.builder()
                .username(userDto.getUsername())
                .password(MD5.create().digestHex(userDto.getPassword()))
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .company(userDto.getCompany())
                .role(userDto.getRole())
                .status(0)
                .balance(new BigDecimal(0))
                .isdeleted(0)
                .createTime(new Date())
                .updateTime(new Date()).build();
        userMapper.insert(user);
    }

    @Override
    public UserLoginVo login(UserDto userDto) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", userDto.getUsername()));
        if(user == null) {
            throw new UserNotExistException();
        }
        String password = user.getPassword();
        String inputPassword = MD5.create().digestHex(userDto.getPassword());
        if(!password.equals(inputPassword)){
            throw new ErrorPasswordException();
        }
        String token = UUID.fastUUID().toString();
        Long userId=user.getUserid();
        String key = RedisConstant.LOGIN+token;
        redisTemplate.opsForValue().set(key,userId);
        UserLoginVo userLoginVo = UserLoginVo.builder()
                .token(token)
                .status(user.getStatus())
                .role(user.getRole()).build();
        return userLoginVo;
    }
}




