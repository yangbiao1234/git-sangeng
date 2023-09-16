package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-09-12 21:33:19
 */
public interface MenuService extends IService<Menu> {

    /**
     * 响应格式:
     *
     * 如果用户id为1代表管理员，roles 中只需要有admin，
     * permissions中需要有所有菜单类型为C或者F的，状态为正常的，未被删除的权限
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);
}

