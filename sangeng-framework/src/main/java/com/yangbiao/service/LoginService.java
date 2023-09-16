package com.yangbiao.service;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

}