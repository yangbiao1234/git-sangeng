package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}

