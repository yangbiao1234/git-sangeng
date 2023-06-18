package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;

public interface ArticleService extends IService <Article>{

    /**
     * 页面右下角 前十的热门文章
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 主页的文章查询 和通过categoryId来判断是否为分类的文章的查寻（这个方法主页文章的查寻和分类文章的查询）
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
