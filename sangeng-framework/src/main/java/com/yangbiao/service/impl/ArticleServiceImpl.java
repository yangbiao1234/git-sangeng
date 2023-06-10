package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.domain.vo.HotArticleVo;
import com.yangbiao.mapper.ArticleMapper;
import com.yangbiao.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        //必须是正式文章(方法引用)
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条记录(分页第一页查询10条记录)
        Page<Article> page = new Page<Article>(SystemConstants.pag1,10);
        page(page, queryWrapper);

        //符合检索条件对象的所有字段
        List<Article> records = page.getRecords();
        //在次包装
        List<HotArticleVo> articleVos = new ArrayList<HotArticleVo>();
        //bean拷贝
        //records符合查询条件的对象的所有字段过于敏感 用vo在次包装
        for (Article record : records) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(record, vo);
            articleVos.add(vo);
        }







        return ResponseResult.okResult(articleVos);
    }


}
