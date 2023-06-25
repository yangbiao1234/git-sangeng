package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangbiao.domain.entity.LoginUser;
import com.yangbiao.domain.entity.User;
import com.yangbiao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service //（默认的UserDetailsService会在内存中查找所以要重新创建一个UserDetailsService在数据库中查找）
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    //UserDetailsService接口：加载用户特定数据的核心接口。里面定义了一个根据用户名查询用户信息的方法。
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);

        //判断是否查到用户 如果没有查询到用户抛出异常
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("没有查询到对应的用户");
        }
        //返回用户信息
        //TODO 查询权限信息并封装

        //返回的是user  和UserDetails接口的数据类型无关 LoginUser是实现了UserDetails的所以要借用LoginUser来进一步封装
        LoginUser loginUser = new LoginUser(user);

        return loginUser;

    }
}
