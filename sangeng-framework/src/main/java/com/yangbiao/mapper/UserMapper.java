package com.yangbiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangbiao.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-18 16:40:37
 */
public interface UserMapper extends BaseMapper<User> {

    boolean insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    boolean deleteUserRole(@Param("id") Long id);

    List<Long> getRoleIdsByUserId(Long id);
}

