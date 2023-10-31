package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AddArticleDto;
import com.yangbiao.domain.dto.AdminArticleUpdateDto;
import com.yangbiao.domain.entity.Article;

public interface ArticleService extends IService <Article>{

    /**
     * 页面右下角 前十的热门文章
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 主页的文章查询 和通过categoryId来判断是否为分类的文章的查寻（这个方法是主页文章的查寻和分类文章的查询）
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 文章详情页代码实现（在主页点击”阅读全文“通过id进入文章详情页）
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 更新浏览次数 在用户浏览博文时要实现对应博客浏览量的增加。
     * ArticleController中增加方法更新阅读数
     * @param id
     * @return
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 5.8.2.4 新增博文并会对添加博客过程中使用事务注解
     * @param articleDto
     * @return
     */
    ResponseResult add(AddArticleDto articleDto);

    /**
     * 为了对文章进行管理，需要提供文章列表，
     * 在后台需要分页查询文章功能，要求能根据标题和摘要**模糊查询**。
     * 注意：不能把删除了的文章查询出来
     * @param pageNum
     * @param pageSize
     * @param articleDto
     * @return
     */
    ResponseResult adminArticleList(Integer pageNum, Integer pageSize, AddArticleDto articleDto);

    /**
     * 这个功能的实现首先需要能够根据文章id查询文章的详细信息这样才能实现文章的回显。
     * 如何需要提供更新文章的接口。
     * @param id
     * @return
     */
    ResponseResult adminArticleSelect(Long id);

    /**
     * 点击文章列表中的修改按钮可以跳转到写博文页面。回显示该文章的具体信息。
     * ​用户可以在该页面修改文章信息。点击更新按钮后修改文章。
     * @param adminArticleUpdateDto
     * @return
     */
    ResponseResult adminArticleUpdate(AdminArticleUpdateDto adminArticleUpdateDto);

    /**
     * 点击文章后面的删除按钮可以删除该文章
     * 注意：是逻辑删除不是物理删除
     * @param id
     * @return
     */
    ResponseResult adminArticleDelete(Long id);
}
