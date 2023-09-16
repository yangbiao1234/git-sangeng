package com.yangbiao.controller;

import com.yangbiao.annotation.SystemLog;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Article;
import com.yangbiao.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //进入controller中的方法都是在响应体当中的
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 需要查询浏览量最高的前10篇文章的信息。要求展示文章标题和浏览量。把能让用户自己点击跳转到具体的文章详情进行浏览。
     * 注意：不能把草稿展示出来，不能把删除了的文章查询出来。要按照浏览量进行降序排序。
     * @return
     */
    @SystemLog(businessName = "热门文章列表")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result = articleService.hotArticleList();

        return result;
    }

    /**
     * 在首页和分类页面都需要查询文章列表。
     *
     * ​	首页：查询所有的文章
     *
     * ​	分类页面：查询对应分类下的文章
     *
     * ​	要求：①只能查询正式发布的文章 ②置顶的文章要显示在最前面
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    //query类型的参数：相当于在地址后面拼接“?id=1&name=yangbiao”
    //请求参数中对应字段空时 赋给默认的值
    @SystemLog(businessName = "分页查询文章列表")
    @GetMapping("/articleList")
    public ResponseResult articleList(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "1") Integer pageSize,
                                       Long categoryId){

        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    /**
     * 要求在文章列表点击阅读全文时能够跳转到文章详情页面，可以让用户阅读文章正文。
     *
     * ​	要求：①要在文章详情中展示其分类名
     * @param id
     * @return
     */
    @SystemLog(businessName = "文章详情接口")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    /**
     * 更新浏览次数 在用户浏览博文时要实现对应博客浏览量的增加。
     * ArticleController中增加方法更新阅读数
     * @param id
     * @return
     */
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

}
