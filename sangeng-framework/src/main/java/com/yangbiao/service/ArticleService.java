package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;

public interface ArticleService extends IService <Article>{

    ResponseResult hotArticleList();
}
