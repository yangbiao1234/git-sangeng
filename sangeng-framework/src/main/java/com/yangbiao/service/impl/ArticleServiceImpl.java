package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AddArticleDto;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.domain.entity.ArticleTag;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.domain.vo.ArticleDetailVo;
import com.yangbiao.domain.vo.ArticleListVo;
import com.yangbiao.domain.vo.HotArticleVo;
import com.yangbiao.domain.vo.PageVo;
import com.yangbiao.mapper.ArticleMapper;
import com.yangbiao.service.ArticleService;
import com.yangbiao.service.ArticleTagService;
import com.yangbiao.service.CategoryService;
import com.yangbiao.utils.BeanCopyUtils;
import com.yangbiao.utils.RedisCache;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        //必须是正式文章(方法引用)
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条记录(分页第一页查询10条记录)
        Page<Article> page = new Page<Article>(SystemConstants.PAG1,SystemConstants.PAG10);
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
        //Objects.nonNull(categoryId) && categoryId>0 类似于动态sql  true继续执行下边的代码
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId>0,
                Article::getCategoryId,categoryId);

        //状态是正式发布的文章
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);

        //对isTop进行降序排序 是否置顶（0否，1是）
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName 分类名称
        List<Article> articles = page.getRecords();
        articles = articles.stream()
                .map(article ->
                        //把分类名称设置给article
                        //返回这个当前对象本身
                        //@Accessors(chain = true) 在实体类中的注解
                        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                //收集流里的数据
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos =
                BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        //PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(new PageVo(articleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);

        //转化为VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名
       Long categoryId = articleDetailVo.getCategoryId();

       Category category = categoryService.getById(categoryId);

        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }

        //封装相应返回
      return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.REDISKEY,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional //事务的原子性 @Transactional可以作用于接口、类或类方法
    public ResponseResult add(AddArticleDto articleDto) {
        //添加博客一共分两步
        //第一步：添加博客   小提示：如果插入成功插入对应的Id会返回对应当前类id属性
        //调用save()方法将Article对象保存到数据库中。如果插入成功，会返回对应当前类插入的id属性。
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        //第二部插如关联表 sg_article_tag
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //在 sg_article_tag 表中添加博客和标签的关系
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

}
