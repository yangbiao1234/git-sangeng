package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AdminRoleDto;
import com.yangbiao.domain.dto.RoleDto;
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
    public ResponseResult adminRoleList(@RequestParam Integer pageNum, Integer pageSize, Role role){

        return roleService.adminRoleList(pageNum,pageSize,role);
    }

    /**
     * 改变角色状态
     * @param roleDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult adminRoleList(@RequestBody RoleDto roleDto){

        return roleService.adminRolePut(roleDto);
    }

    /**
     * 新增角色
     * @param adminRoleDto
     * @return
     */
    @PostMapping
    public ResponseResult adminRolePost(@RequestBody AdminRoleDto adminRoleDto){

        return roleService.adminRolePost(adminRoleDto);
    }

    /**
     * 修改角色
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult adminRoleSelect(@PathVariable Long id){

        return roleService.adminRoleSelect(id);
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @PutMapping
    public ResponseResult adminRolePut(@RequestBody Role role){

        roleService.adminRolePut(role);
        return ResponseResult.okResult();
    }

    /**
     * 删除固定的某个角色（逻辑删除）
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult adminRoleDelete(@PathVariable Long id){

        return roleService.adminRoleDelete(id);
    }
}
