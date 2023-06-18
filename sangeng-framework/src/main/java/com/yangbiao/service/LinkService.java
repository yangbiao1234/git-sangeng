package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-06-18 11:45:18
 */
public interface LinkService extends IService<Link> {

    /**
     * 查询全部友链
     * @return
     */
    ResponseResult getAllLink();
}

