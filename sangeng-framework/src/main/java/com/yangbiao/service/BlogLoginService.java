package com.yangbiao.service;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;

public interface BlogLoginService {
    /**
     * 登录
     *
     * ​	①自定义登录接口
     *
     * ​				调用ProviderManager的方法进行认证 如果认证通过生成jwt
     *
     * ​				把用户信息存入redis中
     *
     * ​	②自定义UserDetailsService
     *
     * ​				在这个实现类中去查询数据库
     *
     * ​	注意配置passwordEncoder为BCryptPasswordEncoder
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出用户
     * 要实现的操作：
     *
     * ​	删除redis中的用户信息
     * @return
     */
    ResponseResult logout();
}
