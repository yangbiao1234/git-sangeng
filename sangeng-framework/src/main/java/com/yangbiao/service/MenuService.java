package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
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
     *响应格式:
     *权限控制
     * 如果用户id为1代表管理员，roles 中只需要有admin，
     * permissions中需要有所有菜单类型为C或者F的，状态为正常的，未被删除的权限
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 响应格式:
     *
     *前端为了实现动态路由的效果，需要后端有接口能返回用户所能访问的菜单数据。
     *注意：**返回的菜单数据需要体现父子菜单的层级关系**
     *如果用户id为1代表管理员，menus中需要有所有菜单类型为C或者M的，状态为正常的，未被删除的权限
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    /**
     *需要展示菜单列表，不需要分页。
     *可以针对菜单名进行模糊查询
     *也可以针对菜单的状态进行查询。
     *菜单要按照父菜单id和orderNum进行排序
     * @param menu
     * @return
     */
    ResponseResult adminMenuList(Menu menu);

    /**
     * 可以新增菜单
     * 请求体参数：
     * ​Menu类对应的json格式
     * @param menu
     * @return
     */
    ResponseResult adminMenuPuts(Menu menu);
}

