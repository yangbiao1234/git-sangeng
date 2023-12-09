package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.entity.Role;
import com.yangbiao.domain.entity.RoleMenu;
import com.yangbiao.domain.entity.Tag;
import com.yangbiao.domain.vo.MenuTreeSelectAllVo;
import com.yangbiao.domain.vo.MenuTreeSelectVo;
import com.yangbiao.domain.vo.MenuTreeVo;
import com.yangbiao.domain.vo.MenuVo;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.mapper.MenuMapper;
import com.yangbiao.mapper.RoleMapper;
import com.yangbiao.mapper.RoleMenuMapper;
import com.yangbiao.service.MenuService;
import com.yangbiao.service.RoleMenuService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.SecurityUtils;
import com.yangbiao.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.sun.javafx.robot.impl.FXRobotHelper.getChildren;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-09-12 21:33:21
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuService menuService;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuService roleMenuService;


    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 这里要分为两种情况 第一种访问者为超级管理员 第二种访问者为普通用户
        //第一种：如果是超级管理员,返回所有权限
        if (SecurityUtils.isAdmin()) {
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
            //返回菜单权限表中的权限信息
            return perms;
        }
        //第二种：访问者为普通用户 多表联查返回所有权限
        return getBaseMapper().selectPermsByUserId(id);

    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus = null;
        // 这里要分为两种情况 第一种访问者为超级管理员 第二种访问者为普通用户
        //第一种：如果是超级管理员,返回所有符合要求的menu
        //如果用户id为1代表管理员，menus中需要有所有菜单类型为C或者M的，状态为正常的，未被删除的权限
        if (SecurityUtils.isAdmin()) {
            menus = baseMapper.selectAllRouterMenu();
        } else {
            //第二种：访问者为普通用户 多表联查返回当前用户所具有的menu
            menus = getBaseMapper().selectRouterMenuTreeByUserId(userId);
        }
        
        //构建children：第二级菜单中的内容 采用了递归查找
        //先找出第一层的菜单，然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0l);
        return menus;
    }

    @Override
    public List<Menu> adminMenuList(Menu menu) {
        //列表查询用list
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper();
        wrapper.like(StringUtils.hasText(menu.getMenuName()), Menu::getMenuName, menu.getMenuName());
        wrapper.eq(StringUtils.hasText(menu.getStatus()), Menu::getStatus, menu.getStatus());
        //MP会自动查询未被删除的权限 所有不用写条件
        //.map可以对流中的元素进行计算或者转化
        List<Menu> menus = list(wrapper);

        //集合数据按照 orderNum字段进行排序
        List<Menu> adminMenuList = list().stream()
                .sorted((o1, o2) -> o1.getOrderNum() - o2.getOrderNum())
                .collect(Collectors.toList());


        return adminMenuList;
    }

    @Override
    public ResponseResult adminMenuPuts(Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminMenuSelectId(Long id) {

        Menu menu = menuMapper.selectById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult adminMenuPut(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId, menu.getId());
        menuMapper.update(menu, queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminMenuDelete(long id) {
        //能够删除菜单，但是如果要删除的菜单有子菜单则提示：存在子菜单不允许删除 并且删除失败。
        Menu menu = menuMapper.selectById(id);
        if (SystemConstants.PARENT_ID.equals(menu.getParentId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARENTID_NULL);
        }
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId, menu.getId());
        menuMapper.delete(queryWrapper);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Long id) {

        //获取所有权限
        List<Menu> menus = list();
        //查询菜单树
        List<MenuTreeSelectVo> treeSelectChildren = getTreeSelectChildren(menus, SystemConstants.PARENT_ID_LONG);
        //已选菜单
        List<RoleMenu> menuIds = roleMenuService.lambdaQuery().eq(RoleMenu::getRoleId, id).list();
        List<Long> collect = menuIds.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        //封装返回
        MenuTreeSelectAllVo menuTreeSelectAllVo = new MenuTreeSelectAllVo(treeSelectChildren, collect);
        return ResponseResult.okResult(menuTreeSelectAllVo);
    }

    /**
     * 这段代码的作用是根据给定的父级菜单ID，递归地获取该菜单下的所有子菜单，并将它们封装为MenuTreeSelectVo对象列表返回。
     */
    private List<MenuTreeSelectVo> getTreeSelectChildren(List<Menu> menus, Long parentId) {

        //菜单权限表 menu.getParentId() 父菜单id表示为"0"表示为父菜单
        return menus.stream()
                .filter( menu -> menu.getParentId().equals(parentId))
                .map(menu ->
                {
                    MenuTreeSelectVo menuTreeSelectVo = BeanCopyUtils.copyBean(menu, MenuTreeSelectVo.class);
                    menuTreeSelectVo.setChildren(getTreeSelectChildren(menus,menu.getId()));
                    menuTreeSelectVo.setLabel(menu.getMenuName());
                    return menuTreeSelectVo;
                })
                .collect(Collectors.toList());
    }


    /**
     * 先找出第一层的菜单，然后去找他们的子菜单设置到children属性中
     * .filter()  符合括号中筛选条件的值留下
     * @param menus
     * @param parentId
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取入参Menu的子Menu
     * .filter()  符合括号中筛选条件的值留下
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        return childrenList;
    }
}

