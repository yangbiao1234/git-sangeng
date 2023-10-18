package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AddTagDto;
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

    /**
     * 点击标签管理的新增按钮可以实现新增标签的功能。
     * @param tagDto
     * @return
     */
    ResponseResult addTag(AddTagDto tagDto);


    /**
     * 路径：content/tag/6   代表删除id为6的标签数据
     * @param id
     * @return
     */
    ResponseResult deleteTag(Long id);

    /**
     * 修改标签第一步点击修改标签查询到要修改的标签
     * 获取标签信息
     * 路径：content/tag/6   代表获取id为6的标签数据
     * @param id
     * @return
     */
    ResponseResult getTag(Long id);

    /**
     * 修改标签第二部根据id修改标签
     * 修改标签接口
     * @param tagDto
     * @return
     */
    ResponseResult updateTagContent(AddTagDto tagDto);
//
//    ResponseResult updateTag(Tag tag);

   // List<TagVo> listTag();
}

