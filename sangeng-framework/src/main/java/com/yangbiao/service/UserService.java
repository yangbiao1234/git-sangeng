package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-08-05 22:12:27
 */
public interface UserService extends IService<User> {

    /**
     * 进入个人中心的时候需要能够查看当前用户信息
     * @return
     */
    ResponseResult userInfo();

    /**
     * 在编辑完个人资料后点击保存会对个人资料进行更新。
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResponseResult register(User user);
}

