package com.yangbiao.controller;


import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.domain.vo.MenuTreeVo;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.service.ArticleService;
import com.yangbiao.service.MenuService;
import com.yangbiao.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表
     * @param menu
     * @return
     */
    @GetMapping("/list") //@RequestParam注解将请求参数绑定到方法的参数上。
    public ResponseResult adminMenuList(Menu menu){

        return ResponseResult.okResult(menuService.adminMenuList(menu));
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult adminMenuPuts(@RequestBody Menu menu){

        return menuService.adminMenuPuts(menu);
    }

    /**
     * 修改菜单第一步按照id回显出当前id的全部信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult adminMenuSelectId(@PathVariable Long id){

        return menuService.adminMenuSelectId(id);
    }

    /**
     * 修改菜单第二步根据回显的信息进行修改
     * @param menu
     * @return
     */
    @PutMapping
    public ResponseResult adminMenuPut(@RequestBody Menu menu){

        //如果把父菜单设置为当前菜单:
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),
                    "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return menuService.adminMenuPut(menu);
    }


    /**
     * 能够删除菜单，但是如果要删除的菜单有子菜单则提示：存在子菜单不允许删除 并且删除失败。
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult adminMenuPut(@PathVariable long id){

        return menuService.adminMenuDelete(id);
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.adminMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

}
