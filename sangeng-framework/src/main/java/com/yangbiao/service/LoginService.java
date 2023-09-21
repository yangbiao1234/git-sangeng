package com.yangbiao.service;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;

public interface LoginService {
    /**
     * 登录接口
     * @param user
     * @return
     */
    ResponseResult login(User user);

}