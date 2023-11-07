package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.RoleDto;
import com.yangbiao.domain.entity.Role;
import com.yangbiao.domain.vo.PageVo;
import com.yangbiao.domain.vo.RoleVo;
import com.yangbiao.mapper.RoleMapper;
import com.yangbiao.service.RoleService;
import com.yangbiao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.role;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-09-12 21:42:51
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //这里要分为两种情况 第一种访问者为超级管理员 第二种访问者为普通用户
        //第一种：如果是超级管理员，返回集合中只需要有admin
        if (id == 1L) {
            List<String> rolekeys = new ArrayList<String>();
            rolekeys.add("admin");
            return rolekeys;
        }
        //第二种：访问者为普通用户 多表联查返回所有角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult adminRoleList(Integer pageNum, Integer pageSize, Role role) {
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        //列表查询用list
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper();
        //要求能够针对角色名称进行模糊查询。
        wrapper.like(StringUtils.hasText(roleVo.getRoleName()), Role::getRoleName, roleVo.getRoleName());
        //要求能够针对状态进行查询。
        wrapper.eq(StringUtils.hasText(roleVo.getStatus()), Role::getStatus, roleVo.getStatus());
        //要求按照role_sort进行升序排列。
        wrapper.orderByAsc(Role::getRoleSort);
        //MP会自动查询未被删除的权限 所有不用写条件

        //分页查询
        Page<Role> adminRole = new Page<>();
        adminRole.setCurrent(pageNum);
        adminRole.setSize(pageSize);
        page(adminRole, wrapper);

        //封装数据返回
        PageVo pageVo = new PageVo(adminRole.getRecords(), adminRole.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult adminRolePut(RoleDto roleDto) {
        //角色状态（0正常 1停用）
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);

        //前端传来的值：roleId  后端对应的值：id 所以更新时采用更新的条件构造器
        //也可用 @JsonField 是一个用于指定 JSON 字段名称的注解，通常用于 Java 对象与 JSON 数据之间的映射。
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId,roleDto.getRoleId());
        roleMapper.update(role,wrapper);
        return ResponseResult.okResult();
    }
}

