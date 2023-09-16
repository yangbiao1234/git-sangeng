package com.yangbiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangbiao.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-12 21:42:46
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询条件复杂，需自己定义sql。
     * 第一步点击“RoleMapper”生成mapper映射文件。
     * 第二步点击方法名“selectRoleKeyByUserId”生成标签
     * @param userId
     * @return
     */
    List<String> selectRoleKeyByUserId(Long userId);
}

