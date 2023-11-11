package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AdminRoleDto;
import com.yangbiao.domain.dto.RoleDto;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-09-12 21:42:49
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    /**
     * 需要有角色列表分页查询的功能。
     * 要求能够针对角色名称进行模糊查询。
     * 要求能够针对状态进行查询。
     * 要求按照role_sort进行升序排列。
     *
     * Query格式请求参数：
     * pageNum: 页码
     * pageSize: 每页条数
     * roleName：角色名称
     * status：状态
     * @param role
     * @return
     */
    ResponseResult adminRoleList(Integer pageNum, Integer pageSize, Role role);

    /**
     * 改变角色状态，要求能够修改角色的停启用状态。
     * @param roleDto
     * @return
     */
    ResponseResult adminRolePut(RoleDto roleDto);

    /**
     * 系统管理子菜单角色管理点击新增两个表连表新增
     * @param adminRoleDto
     * @return
     */
    ResponseResult adminRolePost(AdminRoleDto adminRoleDto);

    /**
     * 需要提供修改角色的功能。修改角色时可以修改角色所关联的菜单权限
     * 第一步：角色信息回显接口
     * @param id
     * @return
     */
    ResponseResult adminRoleSelect(Long id);
}

