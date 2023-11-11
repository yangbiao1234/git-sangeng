package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.entity.RoleMenu;
import com.yangbiao.mapper.RoleMenuMapper;
import com.yangbiao.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-11-11 20:53:59
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

