package com.yangbiao.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token

        //解析获取的token

        //从redis中获取用户信息（两种情况 1.如果redis中有redis信息证明登陆过 2.用户信息过期或者没有登陆都需要重新进行登录）

        //存入SecurityContextHolder
    }
}
