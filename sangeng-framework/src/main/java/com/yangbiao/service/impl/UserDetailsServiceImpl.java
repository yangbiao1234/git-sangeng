package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.entity.LoginUser;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.entity.User;
import com.yangbiao.mapper.MenuMapper;
import com.yangbiao.mapper.UserMapper;
import com.yangbiao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static sun.security.jgss.GSSUtil.login;

@Service //（默认的UserDetailsService会在内存中查找所以要重新创建一个UserDetailsService在数据库中查找）
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;;

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
        if(user.getType().equals(SystemConstants.ADMAIN)){
            //注意：这里查询用户权限信息不能用MenuServiceImpl，因为管理员和普通用户是两种逻辑
            //而本处代码只是利用id来查询权限信息
            List<String> menus = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, menus);
        }
            //返回的是user  和UserDetails接口的数据类型无关 LoginUser是实现了UserDetails的所以要借用LoginUser来进一步封装
            return new LoginUser(user, null);
    }
}
