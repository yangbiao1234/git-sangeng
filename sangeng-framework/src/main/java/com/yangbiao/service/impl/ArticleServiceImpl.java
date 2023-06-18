package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.domain.vo.ArticleListVo;
import com.yangbiao.domain.vo.HotArticleVo;
import com.yangbiao.domain.vo.PageVo;
import com.yangbiao.mapper.ArticleMapper;
import com.yangbiao.service.ArticleService;
import com.yangbiao.service.CategoryService;
import com.yangbiao.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        //必须是正式文章(方法引用)
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条记录(分页第一页查询10条记录)
        Page<Article> page = new Page<Article>(SystemConstants.pag1,SystemConstants.pag10);
        page(page, queryWrapper);

        //符合检索条件对象的所有字段
        List<Article> records = page.getRecords();

//        //在次包装
//        List<HotArticleVo> articleVos = new ArrayList<HotArticleVo>();
//        //bean拷贝
//        //records符合查询条件的对象的所有字段过于敏感 用vo在次包装
//        for (Article record : records) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(record, vo);
//            articleVos.add(vo);
//        }

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);


        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件（categoryId前端传来值代表要查询分类的文章 没有传值代表查主页的文章）
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper();

        //如果有categoryId 就要 查询时要和传入的相同
        //Objects.nonNull(categoryId) && categoryId>0 类似于动态sql
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId>0,
                Article::getCategoryId,categoryId);

        //状态是正式发布的文章
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);

        //对isTop进行降序排序 是否置顶（0否，1是）
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<Article>(pageNum, pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName 分类名称
        List<Article> articles = page.getRecords();
        //articleId去查询articleName进行设置
        for (Article article : articles) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }

        //封装查询结果
        List<ArticleListVo> articleListVos =
                BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        //PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }


}
