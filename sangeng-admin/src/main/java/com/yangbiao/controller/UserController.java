package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.UserDto;
import com.yangbiao.domain.entity.Role;
import com.yangbiao.domain.vo.SimpleRoleVo;
import com.yangbiao.domain.vo.UpdateUserVo;
import com.yangbiao.service.RoleService;
import com.yangbiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 查询用户列表
     * @param pageNum
     * @param pageSize
     * @param UserDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult adminUserList(@RequestParam Integer pageNum,
                                        Integer pageSize,
                                        UserDto UserDto){

        return userService.adminUserList(pageNum,pageSize,UserDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('system:user:add')")
    public ResponseResult adminAddUser(@RequestBody @Validated(UserDto.Add.class) UserDto userDto){
        return userService.adminAddUser(userDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:user:remove')")
    public ResponseResult adminDeleteUser(@PathVariable Long id){
        return userService.adminDeleteUser(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")
    public ResponseResult adminGetUserInfoById(@PathVariable Long id){
        UpdateUserVo userVo = userService.adminGetUserInfoById(id);
        ResponseResult<List<SimpleRoleVo>> roles = roleService.adminGetAllRole();
        userVo.setRoles(roles.getData());
        return ResponseResult.okResult(userVo);
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('system:user:edit')")  //@Validated(UserDto.Update.class)
    public ResponseResult updateUserInfo(@RequestBody  UserDto userDto){
        return userService.adminUpdateUser(userDto);
    }
}
