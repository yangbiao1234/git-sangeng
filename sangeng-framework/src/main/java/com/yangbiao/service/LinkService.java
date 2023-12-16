package com.yangbiao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.LinkDto;
import com.yangbiao.domain.dto.LinkPutDto;
import com.yangbiao.domain.dto.PageDto;
import com.yangbiao.domain.dto.QueryLinkDto;
import com.yangbiao.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-06-18 11:45:18
 */
public interface LinkService extends IService<Link> {

    /**
     * 查询全部友链
     * @return
     */
    ResponseResult getAllLink();

    /**
     *需要分页查询友链列表。
     *能根据友链名称进行模糊查询。
     *能根据状态进行查询。
     * @return
     */
    ResponseResult adminListLink(PageDto pageDto,
                                 QueryLinkDto queryLinkDto);

    /**
     * 需要新增友链功能
     * @param linkDto
     * @return
     */
    ResponseResult adminLink(LinkDto linkDto);

    /**
     * 修改友链
     * 第一步：根据id查询友联
     * @param id
     * @return
     */
    ResponseResult adminLinkGetById(Long id);

    /**
     * 修改友链
     * 第二步：根据id查询友联后修改友链
     * @param linkPutDto
     * @return
     */
    ResponseResult adminLinkPut(LinkPutDto linkPutDto);

    /**
     * 删除某个友链（逻辑删除）
     * @param id
     * @return
     */
    ResponseResult adminLinkDelete(Long id);
}

