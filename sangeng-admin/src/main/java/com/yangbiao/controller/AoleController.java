package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/role")
public class AoleController {

    /**
     * 查询 角色列表
     * @param menu
     * @return
     */
    @GetMapping("/list") //@RequestParam注解将请求参数绑定到方法的参数上。
    public ResponseResult adminMenuList(Menu menu){

        return menuService.adminMenuList(menu);
    }
}
