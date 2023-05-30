package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.mapper.ArticleMapper;
import com.yangbiao.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        //必须是正式文章(方法引用)
        queryWrapper.eq(Article::getStatus,0);
        //按照浏览量进行排序
        queryWrapper.orderByAsc(Article::getViewCount);
        //最多只查询10条记录(分页第一页查询10条记录)
        Page<Article> page = new Page<Article>(1,10);
        page(page, queryWrapper);

        List<Article> records = page.getRecords();



        return ResponseResult.okResult(records);
    }


}
