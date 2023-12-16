package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.UserDto;
import com.yangbiao.domain.entity.User;
import com.yangbiao.domain.vo.UpdateUserVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-08-05 22:12:27
 */
public interface UserService extends IService<User> {

    /**
     * 进入个人中心的时候需要能够查看当前用户信息
     *
     * @return
     */
    ResponseResult userInfo();

    /**
     * 在编辑完个人资料后点击保存会对个人资料进行更新。
     *
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    ResponseResult register(User user);

    /**
     * 需要用户分页列表接口。
     * 可以根据用户名模糊搜索。
     * 可以进行手机号的搜索。
     * 可以进行状态的查询。 账号状态（0正常 1停用）
     *
     * @param pageNum  页码
     * @param pageSize 每页数据
     * @param userDto  用户名，手机号，状态（集合）
     * @return
     */
    ResponseResult adminUserList(Integer pageNum, Integer pageSize, UserDto userDto);

    /**
     * 新增用户
     * 规则：需要新增用户功能。新增用户时可以直接关联角色。
     * 注意：新增用户时注意密码加密存储。
     * 用户名不能为空，否则提示：必需填写用户名
     * 用户名必须之前未存在，否则提示：用户名已存在
     * 手机号必须之前未存在，否则提示：手机号已存在
     * 邮箱必须之前未存在，否则提示：邮箱已存在
     *
     * @param userDto
     * @return
     */
    ResponseResult adminAddUser(UserDto userDto);

    /**
     * 删除固定的某个用户（逻辑删除）
     * 不能删除当前操作的用户
     * @param id
     * @return
     */
    ResponseResult adminDeleteUser(Long id);

    /**
     *需要提供修改用户的功能。修改用户时可以修改用户所关联的角色。
     * 第一步：根据id查询用户信息回显接口
     * @param id
     * @return
     */
    UpdateUserVo adminGetUserInfoById(Long id);

    /**
     *第一步：更新用户信息接口
     * @param userDto
     * @return
     */
    ResponseResult<Object> adminUpdateUser(UserDto userDto);
}

