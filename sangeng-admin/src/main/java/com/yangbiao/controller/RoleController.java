package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.RoleDto;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.entity.Role;
import com.yangbiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    @GetMapping("/list")
    public ResponseResult adminMenuList(@RequestParam Integer pageNum, Integer pageSize, Role role){

        return roleService.adminRoleList(pageNum,pageSize,role);
    }

    /**
     * 改变角色状态
     * @param roleDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult adminMenuList(@RequestBody RoleDto roleDto){

        return roleService.adminRolePut(roleDto);
    }
}
