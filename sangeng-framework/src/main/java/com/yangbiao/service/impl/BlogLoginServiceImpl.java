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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired //默认情况下容器中没有 需要自己创建authenticationManager
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    //Authentication接口: 它的实现类，表示当前访问系统的用户，封装了用户相关信息。
    //AuthenticationManager接口：定义了认证Authentication的方法
    @Override
    public ResponseResult login(User user) {

        //接口authenticationManager 实现类 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

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
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(vo);
    }
}
