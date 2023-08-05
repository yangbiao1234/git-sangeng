package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.entity.User;
import com.yangbiao.mapper.UserMapper;
import com.yangbiao.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-08-05 22:13:07
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

