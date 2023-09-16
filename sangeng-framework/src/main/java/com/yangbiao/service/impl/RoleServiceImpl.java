package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.entity.Role;
import com.yangbiao.mapper.RoleMapper;
import com.yangbiao.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-09-12 21:42:51
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

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
}

