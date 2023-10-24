package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.entity.ArticleTag;
import com.yangbiao.mapper.ArticleTagMapper;
import com.yangbiao.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-10-23 22:51:19
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

