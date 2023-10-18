package com.yangbiao.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yangbiao.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充对应字段 P-49
 */
// 定义一个名为MyMetaObjectHandler的类，实现MetaObjectHandler接口
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 重写insertFill方法，用于在插入数据时自动填充创建时间和创建者信息
    @Override
    public void insertFill(MetaObject metaObject) {
        // 初始化用户ID为null
        Long userId = null;
        try {
            // 获取当前登录用户的ID
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            // 如果获取用户ID失败，打印异常堆栈信息，并将用户ID设置为-1，表示是自己创建的数据
            e.printStackTrace();
            userId = -1L; //表示是自己创建
        }
        // 使用setFieldValByName方法设置创建时间字段的值
        this.setFieldValByName("createTime", new Date(), metaObject);
        // 使用setFieldValByName方法设置创建者字段的值
        this.setFieldValByName("createBy", userId, metaObject);
        // 使用setFieldValByName方法设置更新时间字段的值
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 使用setFieldValByName方法设置更新者字段的值
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    // 重写updateFill方法，用于在更新数据时自动填充更新时间和更新者信息
    @Override
    public void updateFill(MetaObject metaObject) {
        // 使用setFieldValByName方法设置更新时间字段的值
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 使用setFieldValByName方法设置更新者字段的值
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}