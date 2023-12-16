package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.LinkDto;
import com.yangbiao.domain.dto.LinkPutDto;
import com.yangbiao.domain.dto.PageDto;
import com.yangbiao.domain.dto.QueryLinkDto;
import com.yangbiao.domain.entity.Category;
import com.yangbiao.domain.entity.Link;
import com.yangbiao.domain.vo.*;
import com.yangbiao.mapper.LinkMapper;
import com.yangbiao.service.LinkService;
import com.yangbiao.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.yangbiao.constants.SystemConstants.LINK_STATUS_NORMAL;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-06-18 11:45:20
 */
@Service("linkService")
public class  LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        //通过IService接口调用list方法
        List<Link> lists = list(queryWrapper);

        //转换成Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(lists, LinkVo.class);

        //封装返回
       return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult adminListLink(PageDto pageDto,
                                        QueryLinkDto queryLinkDto) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(queryLinkDto.getName()),Link::getName, queryLinkDto.getName())
                .eq(queryLinkDto.getStatus()!=null,Link::getStatus, queryLinkDto.getStatus());

        Page<Link> linkPage = page(new Page<>(pageDto.getPageNum(), pageDto.getPageSize()), wrapper);
        List<Link> linkPageList = linkPage.getRecords();

        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(linkPageList, LinkListVo.class);
        return ResponseResult.okResult(new PageVo(linkListVos, linkPage.getTotal()));
    }

    @Override
    public ResponseResult adminLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        //插入一条记录（选择字段，策略插入）
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminLinkGetById(Long id) {
        Link link = getById(id);
        LinkByIdVo linkByIdVo= BeanCopyUtils.copyBean(link, LinkByIdVo.class);
        return ResponseResult.okResult(linkByIdVo);
    }

    @Override
    public ResponseResult adminLinkPut(LinkPutDto linkPutDto) {
        Link link = BeanCopyUtils.copyBean(linkPutDto, Link.class);
        //插入一条记录（选择字段，策略插入）
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminLinkDelete(Long id) {
        //根据 ID 删除
        removeById(id);
        return ResponseResult.okResult();
    }

}

