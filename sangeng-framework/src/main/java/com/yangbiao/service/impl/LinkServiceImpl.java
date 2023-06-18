package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.constants.SystemConstants;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Link;
import com.yangbiao.domain.vo.LinkVo;
import com.yangbiao.mapper.LinkMapper;
import com.yangbiao.service.LinkService;
import com.yangbiao.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yangbiao.constants.SystemConstants.LINK_STATUS_NORMAL;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-06-18 11:45:20
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


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
}

