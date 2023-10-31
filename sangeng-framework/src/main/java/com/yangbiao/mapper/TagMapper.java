package com.yangbiao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangbiao.domain.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-10 21:44:55
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Long> selectTagId(Long id);
}

