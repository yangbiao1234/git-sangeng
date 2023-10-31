package com.yangbiao.service.impl;

import com.yangbiao.mapper.MenuMapper;
import com.yangbiao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps") //表示将名为 "ps" 的服务注册到 Spring 容器中。
public class PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission
     * @return
     */

    public boolean hasPermission(String permission){

        //判断是否为管理员用户
        if(SecurityUtils.isAdmin()){
            //如果是管理员用户，允许访问
            return true;
        } else{
            //如果不是管理员用户，要查询对应的权限信息在看是否允许访问
            List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
            //用于检查一个集合（permissions）中是否包含某个元素（permission）。如果包含，返回true；否则，返回false。
            return permissions.contains(permission);
        }

    }
}
