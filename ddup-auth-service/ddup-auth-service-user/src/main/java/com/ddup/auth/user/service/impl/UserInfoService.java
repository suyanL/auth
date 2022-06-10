package com.ddup.auth.user.service.impl;

import com.ddup.auth.user.dto.UserDto;
import com.ddup.auth.user.service.IUserInfoService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author suancyg
 */
@DubboService(group = "ddup-auth-user", version = "v1.0.0")
public class UserInfoService implements IUserInfoService {
    @Override
    public UserDto getUserByUserName(String userName) {
        return UserDto.builder().userName(userName).email("suyanl728@183.com").phone("17639853212").status("0").password("000000").build();
    }

    @Override
    public UserDto getUserByUserPhone(String phone) {
        return null;
    }

    @Override
    public UserDto getUserByUserEmail(String email) {
        return null;
    }

    @Override
    public UserDto getUserByUserId(Integer id) {
        return null;
    }
}
