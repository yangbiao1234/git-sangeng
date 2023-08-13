package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;
import com.yangbiao.domain.vo.UserInfoVo;
import com.yangbiao.mapper.UserMapper;
import com.yangbiao.service.UserService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-08-05 22:13:07
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询对应的用户信息
        User user = getById(userId);
        //封装到UserInfoVo中
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        //两次类型转换 防止恶意拼接请求获取敏感字段
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        User user1 = BeanCopyUtils.copyBean(userInfoVo, User.class);

        //根据 ID 选择修改
        updateById(user1);

        return ResponseResult.okResult();
    }
}

