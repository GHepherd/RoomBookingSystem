package com.scau.service;

import com.scau.entity.dto.StaffPageDto;
import com.scau.entity.dto.UserBalanceDto;
import com.scau.entity.dto.UserDto;
import com.scau.entity.dto.UserPageDto;
import com.scau.entity.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scau.entity.vo.AdminGetStaffsPageVo;
import com.scau.entity.vo.UserGetVo;
import com.scau.entity.vo.UserLoginVo;
import com.scau.entity.vo.AdminGetUsersPageVo;

/**
* @author ASUS
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-04-23 00:20:53
*/
public interface UserService extends IService<User> {

    void register(UserDto userDto) ;

    UserLoginVo login(UserDto userDto);

    void createStaff(UserDto userDto);

    void updateUserStatus(Long userId, Integer status);

    AdminGetUsersPageVo getUsers(UserPageDto userPageDto);

    AdminGetStaffsPageVo getStaffs(StaffPageDto staffPageDto);


    UserGetVo getUser();

    void updateUser(UserDto userDto);

    void rechargeUser(UserBalanceDto userBalanceDto);
}
