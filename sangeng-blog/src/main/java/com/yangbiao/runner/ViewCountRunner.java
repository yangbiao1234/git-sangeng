package com.yangbiao.runner;

import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.mapper.ArticleMapper;
import com.yangbiao.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 在应用启动时把博客的浏览量存储到redis中
     *
     * ​实现CommandLineRunner接口，在应用启动时初始化缓存。
     * @param args
     * @throws Exception
     */
        @Override
        public void run(String... args) throws Exception {
            //查询博客信息  id  viewCount
            List<Article> articles = articleMapper.selectList(null);
            Map<String, Integer> viewCountMap = articles.stream()
                    .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                        return article.getViewCount().intValue();//
                    }));
            //存储到redis中
            redisCache.setCacheMap(SystemConstants.REDISKEY,viewCountMap);
        }
    }
