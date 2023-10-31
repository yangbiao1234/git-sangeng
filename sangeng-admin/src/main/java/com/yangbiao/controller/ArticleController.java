package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AddArticleDto;
import com.yangbiao.domain.dto.AdminArticleUpdateDto;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 新增博文接口
     */
    @PostMapping
    public ResponseResult add(AddArticleDto articleDto) {

        return articleService.add(articleDto);
    }

    /**
     * 文章列表
     */
    @GetMapping("/list")
    public ResponseResult adminArticleList(Integer pageNum,Integer pageSize,AddArticleDto articleDto){

        return articleService.adminArticleList(pageNum,pageSize,articleDto);
    }

    /**
     * 点击文章列表中的修改按钮可以跳转到写博文页面。回显示该文章的具体信息。
     * 用户可以在该页面修改文章信息。点击更新按钮后修改文章。 分为两个方法
     */
    @GetMapping("/{id}") //第一个方法 查询回显
    public ResponseResult adminArticleSelect(@PathVariable("id") Long id){

        return articleService.adminArticleSelect(id);
    }

    /**
     * 更新文章接口
     * @param adminArticleUpdateDto
     * @return
     */
    @PutMapping //第二个方法 更新数据
    public ResponseResult adminArticleUpdate(@RequestBody AdminArticleUpdateDto adminArticleUpdateDto){

        return articleService.adminArticleUpdate(adminArticleUpdateDto);
    }


    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult adminArticleDelete(@PathVariable("id") Long id){

        return articleService.adminArticleDelete(id);
    }

}
