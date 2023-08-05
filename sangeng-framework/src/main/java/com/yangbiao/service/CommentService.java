package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-08-05 20:48:56
 */
public interface CommentService extends IService<Comment> {

    /**
     * 文章详情页面要展示这篇文章下的评论列表。
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}

