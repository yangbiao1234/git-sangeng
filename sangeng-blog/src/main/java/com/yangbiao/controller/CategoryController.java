package com.yangbiao.controller;


import com.yangbiao.annotation.SystemLog;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //进入controller中的方法都是在响应体当中的
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     *页面上需要展示分类列表，用户可以点击具体的分类查看该分类下的文章列表。
     * ​注意： ①要求只展示有发布正式文章的分类 ②必须是正常状态的分类
     * @return
     */
    @SystemLog(businessName = "查询分类列表")
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){

        return categoryService.getCategoryList();
    }
}
