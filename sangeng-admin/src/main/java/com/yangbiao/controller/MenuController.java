package com.yangbiao.controller;


import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Menu;
import com.yangbiao.service.ArticleService;
import com.yangbiao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list") //@RequestParam注解来将请求参数绑定到方法的参数上。
    public ResponseResult adminMenuList(Menu menu){

        return menuService.adminMenuList(menu);
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
}
