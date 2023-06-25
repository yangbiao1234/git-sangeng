package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.domain.vo.CategoryVo;
import com.yangbiao.mapper.CategoryMapper;
import com.yangbiao.service.ArticleService;
import com.yangbiao.service.CategoryService;
import com.yangbiao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 21:09:21
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //分步查询
        //查询文章表 状态为已发布
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(articleWrapper);
        
        //获取文章的分类id 并去重(Set)   为什么要去重：是因为好多文章对应的是一个分类所以要去重的保证单一性
        Set<Long> categoryId = list.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表 并且是正常状态的分类（status；0）
        List<Category> categories = listByIds(categoryId);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}

