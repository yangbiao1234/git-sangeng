package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.TagListDto;
import com.yangbiao.domain.entity.Tag;
import com.yangbiao.domain.vo.PageVo;
import org.springframework.stereotype.Service;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-09-10 21:44:58
 */
public interface TagService extends IService<Tag> {

    /**
     * Query格式请求参数：
     *
     * pageNum: 页码
     *
     * pageSize: 每页条数
     *
     * name：标签名
     *
     * remark：备注
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

//    ResponseResult addTag(Tag tag);
//
//    ResponseResult deleteTag(Long id);
//
//    ResponseResult updateTagContent(Long id);
//
//    ResponseResult updateTag(Tag tag);

   // List<TagVo> listTag();
}

