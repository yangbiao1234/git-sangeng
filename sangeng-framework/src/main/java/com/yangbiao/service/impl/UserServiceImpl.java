package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.UserDto;
import com.yangbiao.domain.entity.User;
import com.yangbiao.domain.vo.*;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.exception.BusinessException;
import com.yangbiao.exception.SystemException;
import com.yangbiao.mapper.UserMapper;
import com.yangbiao.service.UserService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {

        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminUserList(Integer pageNum, Integer pageSize, UserDto userDto) {
        UserVo userVo = BeanCopyUtils.copyBean(userDto, UserVo.class);
        //列表查询用list
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        //要求能够针对用户名模糊搜索
        wrapper.like(StringUtils.hasText(userVo.getUserName()), User::getUserName, userVo.getUserName());
        //要求能够针对可以进行手机号的搜索
        wrapper.eq(StringUtils.hasText(userVo.getPhonenumber()), User::getPhonenumber, userVo.getPhonenumber());
        //要求能够针对可以进行可以进行状态的搜索
        wrapper.eq(StringUtils.hasText(userVo.getStatus()), User::getStatus, userVo.getStatus());

        //分页查询
        Page<User> adminUser = new Page<>();
        adminUser.setCurrent(pageNum);
        adminUser.setSize(pageSize);
        page(adminUser, wrapper);

        //封装数据返回
        PageVo pageVo = new PageVo(adminUser.getRecords(), adminUser.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult adminAddUser(UserDto userDto) {
        if (userNameExit(userDto.getUserName()))
            throw new BusinessException(AppHttpCodeEnum.USERNAME_EXIST);
        if (emailExit(userDto.getEmail()))
            throw new BusinessException(AppHttpCodeEnum.EMAIL_EXIST);
        if (phoneExit(userDto.getPhonenumber()))
            throw new BusinessException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        save(user);
        baseMapper.insertUserRole(user.getId(),userDto.getRoleIds());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminDeleteUser(Long id) {
        removeById(id);
        //baseMapper.deleteUserRole(id);
        return ResponseResult.okResult();
    }

    @Override
    public UpdateUserVo adminGetUserInfoById(Long id) {
        User user = getById(id);
        UpdateUserInfoVo updateUserInfoVo = BeanCopyUtils.copyBean(user, UpdateUserInfoVo.class);
        UpdateUserVo updateUserVo = new UpdateUserVo();
        updateUserVo.setUser(updateUserInfoVo);
        List<Long> roleIds = baseMapper.getRoleIdsByUserId(id);
        updateUserVo.setRoleIds(roleIds);
        return updateUserVo;
    }

    @Override
    public ResponseResult<Object> adminUpdateUser(UserDto userDto) {
        //待优化
        User nowUser = getById(userDto.getId());
        if (!nowUser.getUserName().equals(userDto.getUserName()) && userNameExit(userDto.getUserName()))
            throw new BusinessException(AppHttpCodeEnum.USERNAME_EXIST);
        if (!nowUser.getEmail().equals(userDto.getEmail()) && emailExit(userDto.getEmail()))
            throw new BusinessException(AppHttpCodeEnum.EMAIL_EXIST);
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        updateById(user);
        //baseMapper.deleteUserRole(user.getId());
        baseMapper.insertUserRole(user.getId(), userDto.getRoleIds());
        return ResponseResult.okResult();
    }

    private boolean userNameExit(String userName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }

    private boolean emailExit(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper) > 0;
    }
    private boolean phoneExit(String phone){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phone);
        return count(queryWrapper) > 0;
    }


    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        //根据 Wrapper 条件，查询总记录数
        return count(queryWrapper) > 0;

    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        //根据 Wrapper 条件，查询总记录数
        return count(queryWrapper) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        //根据 Wrapper 条件，查询总记录数
        return count(queryWrapper) > 0;
    }
}

