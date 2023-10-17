package com.yangbiao.controller;


import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.LoginUser;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.entity.User;
import com.yangbiao.domain.vo.AdminUserInfoVo;
import com.yangbiao.domain.vo.RoutersVo;
import com.yangbiao.domain.vo.UserInfoVo;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.exception.SystemException;
import com.yangbiao.mapper.MenuMapper;
import com.yangbiao.service.LoginService;
import com.yangbiao.service.MenuService;
import com.yangbiao.service.RoleService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired //菜单权限
    private MenuService menuService;

    @Autowired //角色信息
    private RoleService roleService;

    /**
     * 登录接口
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    /**
     * 退出接口
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    /**
     *1.系统需要能实现不同的用户权限可以看到不同的功能。
     *用户只能使用他的权限所允许使用的功能。
     *
     * 2.如果用户id为1代表超级管理员，roles 中只需要有admin，
     * permissions中需要有所有菜单类型为C或者F的，状态为正常的，未被删除的权限
     * @return
     */
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        Long id = loginUser.getUser().getId();
        Long id = SecurityUtils.getUserId();

        //根据用户Id查询 权限信息（Perms）
        List<String> perms=menuService.selectPermsByUserId(id);

        //根据用户Id查询 角色权限信息（RoleKey）
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(id);

        //获取用户信息
        User user = SecurityUtils.getLoginUser().getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    /**
     * 响应格式:
     *
     *前端为了实现动态路由的效果，需要后端有接口能返回用户所能访问的菜单数据。
     *注意：**返回的菜单数据需要体现父子菜单的层级关系**
     *如果用户id为1代表管理员，menus中需要有所有菜单类型为C或者M的，状态为正常的，未被删除的权限
     * @param
     * @return
     */
    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
