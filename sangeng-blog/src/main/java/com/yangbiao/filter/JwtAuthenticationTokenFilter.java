package com.yangbiao.filter;

import com.alibaba.fastjson.JSON;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.LoginUser;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.utils.JwtUtil;
import com.yangbiao.utils.RedisCache;
import com.yangbiao.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component //认证过滤器代码实现 P37
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析获取的token
        Claims claims = null;
        try {

            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {

            e.printStackTrace();
            //token超时 token非法
            //响应告诉前端需要重新登录  接下来会对controller层进行统一的异常处理但是这个类是filter所以要自行的异常处理
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));

            return;
        }
        String userId = claims.getSubject();

        //从redis中获取用户信息（两种情况 1.如果redis中有redis信息证明登陆过 2.用户信息过期或者没有登陆都需要重新进行登录）
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
        if (Objects.isNull(loginUser)) {
            //说明登录过期 提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));

            return;
        }

        //存入SecurityContextHolder
        //认证状态可以用三个参数未认证用两个参数  super.setAuthenticated(true);  setAuthenticated(false);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //过滤器最后一步执行完了要放行
        filterChain.doFilter(request, response);
    }
}
