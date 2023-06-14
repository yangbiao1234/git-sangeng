package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.mapper.CategoryMapper;
import com.yangbiao.service.CategoryService;
import org.springframework.stereotype.Service;



/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 21:09:21
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


}

