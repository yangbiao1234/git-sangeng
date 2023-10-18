package com.yangbiao.handler.mybatisplus;

// 导入相关类
import java.util.Date;

import com.yangbiao.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 字段自动填充p-49
 * MyMetaObjectHandler类实现了MetaObjectHandler接口，用于处理MyBatis-Plus中的元数据对象。
 * 该类主要用于在插入和更新操作时自动填充创建者和更新者信息。
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * insertFill方法用于在插入操作时自动填充创建者和更新者信息。
     * @param metaObject 元数据对象，包含了实体类的相关信息。
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            // 获取当前登录用户的ID
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            // 如果获取用户ID失败，打印异常堆栈信息，并将userId设置为-1表示是自己创建的
            e.printStackTrace();
            userId = -1L;
        }
        // 设置实体类的创建时间和创建者字段值
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        // 设置实体类的更新时间和更新者字段值
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    /**
     * updateFill方法用于在更新操作时自动填充更新者信息。
     * @param metaObject 元数据对象，包含了实体类的相关信息。
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 设置实体类的更新时间和更新者字段值
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 设置实体类的更新者字段值为当前登录用户的ID
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}