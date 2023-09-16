package com.yangbiao.service.impl;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.LoginUser;
import com.yangbiao.domain.entity.User;
import com.yangbiao.domain.vo.BlogUserLoginVo;
import com.yangbiao.domain.vo.UserInfoVo;
import com.yangbiao.service.BlogLoginService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.JwtUtil;
import com.yangbiao.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired //默认情况下容器中没有 需要自己创建配置类authenticationManager
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    //Authentication接口: 它的实现类，表示当前访问系统的用户，封装了用户相关信息。
    //AuthenticationManager接口：定义了认证Authentication的方法

    @Override
    public ResponseResult login(User user) {

        //接口authenticationManager 实现类 UsernamePasswordAuthenticationToken
        //UsernamePasswordAuthenticationToken 没有认证传两个参数
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        //authenticationManager会调用UserDetailsService进行用户的认证
        //（默认的UserDetailsService会在内存中查找所以要重新创建一个UserDetailsService在数据库中查找）
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);


        /**
         * UserDetailsServiceImpl---------------------------------------------------------------------------
         */


        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userid生成token   authenticate.getPrincipal()  获取身份验证主体
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //生成token进行加密
        String jwt = JwtUtil.createJWT(userId);

        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid   JwtAuthenticationTokenFilter这里是存入loginUser到SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userid
        User user = loginUser.getUser();
        Long id = user.getId();

        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + id);

        return ResponseResult.okResult();
    }
}
