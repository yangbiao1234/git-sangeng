package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.mapper.MenuMapper;
import com.yangbiao.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-09-12 21:33:21
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 这里要分为两种情况 第一种访问者为超级管理员 第二种访问者为普通用户
        //第一种：如果是超级管理员,返回所有权限
        if (id == 1L) {
            //列表查询用list
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper();
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            //MP会自动查询未被删除的权限 所有不用写条件
            //.map可以对流中的元素进行计算或者转化
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(menu -> menu.getPerms())
                    .collect(Collectors.toList());
            return perms;
        }
        //第二种：访问者为普通用户 多表联查返回所有权限
        return getBaseMapper().selectPermsByUserId(id);

    }
}

