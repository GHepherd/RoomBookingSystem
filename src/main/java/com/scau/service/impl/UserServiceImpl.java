package com.scau.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scau.constant.RedisConstant;
import com.scau.entity.dto.StaffPageDto;
import com.scau.entity.dto.UserBalanceDto;
import com.scau.entity.dto.UserDto;
import com.scau.entity.dto.UserPageDto;
import com.scau.entity.pojo.User;
import com.scau.entity.vo.*;
import com.scau.exception.ErrorPasswordException;
import com.scau.exception.UserAlreadyExistException;
import com.scau.exception.UserNotExistException;
import com.scau.exception.UserNotLoginException;
import com.scau.service.UserService;
import com.scau.mapper.UserMapper;
import com.scau.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author ASUS
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-04-23 00:20:53
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void register(UserDto userDto) {
        Assert.notNull(userDto);
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("username", userDto.getUsername()));
        if (count > 0) {
            throw new UserAlreadyExistException();
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .password(MD5.create().digestHex(userDto.getPassword()))
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .company(userDto.getCompany())
                .role(userDto.getRole())
                .status(0)
                .balance(new BigDecimal(0))
                .isDeleted(0)
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
        Long userId=user.getUserId();
        String key = RedisConstant.LOGIN+token;
        redisTemplate.opsForValue().set(key,userId);
        UserLoginVo userLoginVo = UserLoginVo.builder()
                .token(token)
                .status(user.getStatus())
                .role(user.getRole()).build();
        return userLoginVo;
    }

    /**
     * 新建员工
     * @param userDto
     */
    @Override
    public void createStaff(UserDto userDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }

        List<User> usernames = userMapper.selectList(new QueryWrapper<User>().eq("username", userDto.getUsername()));
        if(usernames != null) throw new UserAlreadyExistException();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(MD5.create().digestHex(userDto.getPassword()))
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .role("staff")
                .status(1)
                .build();
        userMapper.insert(user);
    }

    /**
     * 解冻/冻结用户 0解冻 1冻结 2审核   禁用/解禁 staff  0
     * @param userId
     * @param status
     */
    @Override
    public void updateUserStatus(Long userId, Integer status) {
        Long currentUserId = ThreadLocalUtil.getUserId();
        if (currentUserId == null){
            throw new UserNotLoginException();
        }
        if(status == 0) status = 1;
        else if(status == 1) status = 2;
        else if(status == 2) status = 1;

        User user = User.builder()
                .userId(userId)
                .status(status).build();
        userMapper.updateById(user);
    }

    /**
     * 管理员分页查询用户
     * @param userPageDto
     * @return
     */
    @Override
    public AdminGetUsersPageVo getUsers(UserPageDto userPageDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        AdminGetUsersPageVo adminGetUsersPageVo = new AdminGetUsersPageVo();
        Page<User> page = new Page<>(userPageDto.getPage(),userPageDto.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userPageDto.getKeyword() != null){
            queryWrapper.like("username",userPageDto.getKeyword());
        }
        if (userPageDto.getStatus() != null){
            queryWrapper.like("status",userPageDto.getStatus());
        }

        queryWrapper.eq("role","customer");
        Page<User> userPage = userMapper.selectPage(page,queryWrapper);
        List<AdminGetUsersVo> list = userPage.getRecords().stream()
                .map(user -> {
                    AdminGetUsersVo adminGetUsersVo = new AdminGetUsersVo();
                    adminGetUsersVo.setUserId(user.getUserId());
                    adminGetUsersVo.setUsername(user.getUsername());
                    adminGetUsersVo.setName(user.getName());
                    adminGetUsersVo.setPhone(user.getPhone());
                    adminGetUsersVo.setCompany(user.getCompany());
                    adminGetUsersVo.setStatus(user.getStatus());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String datetime = sdf.format(user.getCreateTime());
                    adminGetUsersVo.setCreateTime(datetime);
                    return adminGetUsersVo;
                }).collect(Collectors.toList());
        adminGetUsersPageVo.setList(list);
        adminGetUsersPageVo.setTotal((int) userPage.getTotal());
        return adminGetUsersPageVo;
    }


    /**
     * 管理员分页查询职工
     *
     * @param staffPageDto
     * @return
     */
    @Override
    public AdminGetStaffsPageVo getStaffs(StaffPageDto staffPageDto) {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        AdminGetStaffsPageVo adminGetStaffsPageVo = new AdminGetStaffsPageVo();
        Page<User> page = new Page<>(staffPageDto.getPage(),staffPageDto.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (staffPageDto.getKeyword() != null){
            queryWrapper.like("username",staffPageDto.getKeyword());
        }
        if (staffPageDto.getStatus() != null){
            queryWrapper.like("status",staffPageDto.getStatus());
        }

        queryWrapper.eq("role","staff");
        Page<User> userPage = userMapper.selectPage(page,queryWrapper);
        List<AdminGetStaffsVo> list = userPage.getRecords().stream()
                .map(user -> {
                    AdminGetStaffsVo adminGetStaffsVo = new AdminGetStaffsVo();
                    adminGetStaffsVo.setUserId(user.getUserId());
                    adminGetStaffsVo.setUsername(user.getUsername());
                    adminGetStaffsVo.setName(user.getName());
                    adminGetStaffsVo.setPhone(user.getPhone());
                    adminGetStaffsVo.setStatus(user.getStatus());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String datetime = sdf.format(user.getCreateTime());
                    adminGetStaffsVo.setCreateTime(datetime);
                    return adminGetStaffsVo;
                }).collect(Collectors.toList());
        adminGetStaffsPageVo.setList(list);
        adminGetStaffsPageVo.setTotal((int)userPage.getTotal());
        return  adminGetStaffsPageVo;
    }

    /**
     * 用户查询用户信息
     * @return
     */
    @Override
    public UserGetVo getUser() {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null){
            throw new UserNotLoginException();
        }
        User user = userMapper.selectById(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String datetime = sdf.format(user.getCreateTime());
        UserGetVo userGetVo = UserGetVo.builder()
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .role(user.getRole())
                .company(user.getCompany())
                .status(user.getStatus())
                .balance(user.getBalance())
                .createTime(datetime)
                .build();
        return userGetVo;
    }

    /**
     * 用户更新信息
     * @param userDto
     */
    @Override
    public void updateUser(UserDto userDto) {
        Long currentUserId = ThreadLocalUtil.getUserId();
        if (currentUserId == null){
            throw new UserNotLoginException();
        }
        User user = User.builder()
                .userId(currentUserId)
                .phone(userDto.getPhone())
                .name(userDto.getName())
                .company(userDto.getCompany())
                .password(MD5.create().digestHex(userDto.getPassword()))
                .build();
        userMapper.updateById(user);
    }

    /**
     * 用户充值
     * @param userBalanceDto
     */
    @Override
    public void rechargeUser(UserBalanceDto userBalanceDto) {
        Long currentUserId = ThreadLocalUtil.getUserId();
        if (currentUserId == null){
            throw new UserNotLoginException();
        }
        BigDecimal balance = userMapper.selectById(currentUserId).getBalance();
        User user = User.builder()
                .userId(currentUserId)
                .balance(balance.add(userBalanceDto.getAmount()))
                .build();
        userMapper.updateById(user);
    }
}




