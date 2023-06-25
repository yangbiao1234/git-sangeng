package com.yangbiao.handler.security;

import com.alibaba.fastjson.JSON;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 3.7 认证授权失败处理
 * AuthenticationEntryPoint 认证失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //打印异常信息
        e.printStackTrace();

        ResponseResult response = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(response));
    }
}
