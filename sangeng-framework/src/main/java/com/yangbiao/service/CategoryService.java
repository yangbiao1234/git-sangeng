package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.CategoryDto;
import com.yangbiao.domain.dto.PageDto;
import com.yangbiao.domain.dto.QueryCategoryDto;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-06-14 21:09:20
 */
public interface CategoryService extends IService<Category> {

    /**
     *页面上需要展示分类列表，用户可以点击具体的分类查看该分类下的文章列表。
     *
     *注意： ①要求只展示有发布正式文章的分类 ②必须是正常状态的分类
     * @return
     */
    ResponseResult getCategoryList();

    /**
     * 查寻写博客界面中查询所有分类的信息
     * @return
     */
    List<CategoryVo> listAllCategory();

    /**
     * 需要分页查询分类列表。
     *
     * ​	能根据分类名称进行模糊查询。
     *
     * ​	能根据状态进行查询。
     * @param pageDto
     * @param queryCategoryDto
     * @return
     */
    ResponseResult adminListCategory(PageDto pageDto, QueryCategoryDto queryCategoryDto);

    /**
     *  需要新增分类功能
     * @param categoryDto
     * @return
     */
    ResponseResult adminCategory(CategoryDto categoryDto);

    /**
     * 需要提供修改分类的功能
     * 根据id查询分类
     * @param id
     * @return
     */
    ResponseResult AdminGetCategoryById(Integer id);

    /**
     *  更新分类
     * @param categoryDto
     * @return
     */
    ResponseResult adminUpdateCategory(CategoryDto categoryDto);

    /**
     * 根据id删除某个分类（逻辑删除）
     * @param id
     * @return
     */
    ResponseResult adminDeleteCategory(Long id);
}

