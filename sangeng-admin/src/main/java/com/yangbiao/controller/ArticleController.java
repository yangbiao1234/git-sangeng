package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AddArticleDto;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 新增接口
     */
    @PostMapping
    public ResponseResult add(AddArticleDto articleDto) {

        return articleService.add(articleDto);
    }
}
