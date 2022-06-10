package com.ddup.auth.user.service;

import com.ddup.auth.user.dto.UserDto;

/**
 * @author suancyg
 */
public interface IUserInfoService {

    /**
     * 根据用户名称获取用户信息
     * @param userName 用户名
     * @return dto
     */
    UserDto getUserByUserName(String userName);

    /**
     * 根据手机号获取用户信息
     * @param phone 用户绑定手机号
     * @return dto
     */
    UserDto getUserByUserPhone(String phone);

    /**
     * 根据邮箱获取用户信息
     * @param email 用户绑定邮箱
     * @return dto
     */
    UserDto getUserByUserEmail(String email);

    /**
     * 通过用户ID获取用户信息
     * @param id 用户唯一标识
     * @return dto
     */
    UserDto getUserByUserId(Integer id);
}
