package com.yangbiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangbiao.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-12 21:33:16
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询条件复杂，需自己定义sql。
     * 第一步点击“MenuMapper”生成mapper映射文件。
     * 第二步点击方法名“selectPermsByUserId”生成标签
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(Long userId);
}

