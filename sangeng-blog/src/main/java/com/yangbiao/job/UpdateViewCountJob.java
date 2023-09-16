package com.yangbiao.job;

import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.mapper.ArticleMapper;
import com.yangbiao.service.ArticleService;
import com.yangbiao.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务每隔一段时间把Redis中的浏览量更新到数据库中
 */
@Component
public class UpdateViewCountJob {

//    @Autowired
//    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void updateViewCount() {
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDISKEY);

        //map双列结合(上列集合不能直接转化成流对象) 变为单列结合并批量存入到数据库
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());  //收集作用
        //更新到数据库中  这是IService中批量操作方法
        articleService.updateBatchById(articles);
    }
}

//        List<Article> articles = new ArrayList<Article>();
//        for (Map.Entry<String, Integer> entry : viewCountMap.entrySet()) {
//            // 获取键和值
//            String k = entry.getKey();
//            Integer v = entry.getValue();
//            Article article =new Article();
//            article.setId(Long.valueOf(k));
//            article.setViewCount(v.longValue());
//            articles.add(article);
//        }
//        //批量更新到数据库中
//        articleService.updateBatchById(articles);



//    @Scheduled(cron = "0/5 * * * * ?")
//    public void updateViewCount() {
//        //获取redis中浏览量数据
//        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDISKEY);
//
//        List<Article> articles = viewCountMap.entrySet()
//                .stream()
//                .map(entry ->
//                        new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//                .collect(Collectors.toList());
//
//        for (Article article : articles) {
//            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.eq(Article::getId, article.getId());
//            updateWrapper.set(Article::getViewCount, article.getViewCount());
//            articleService.update(updateWrapper);
//        }

