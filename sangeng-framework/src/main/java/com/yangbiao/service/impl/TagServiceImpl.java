package com.yangbiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.Tag;
import com.yangbiao.domain.vo.PageVo;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.exception.SystemException;
import com.yangbiao.mapper.TagMapper;
import com.yangbiao.service.TagService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 21:45:00
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
//        @Override
//        public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
//            //查询
//            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
//            queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
//            //分页查询
//            Page<Tag> tagPage = new Page<>();
//            tagPage.setCurrent(pageNum);
//            tagPage.setSize(pageSize);
//            page(tagPage,queryWrapper);
//            //封装数据返回
//            PageVo pageVo = new PageVo(tagPage.getRecords(),tagPage.getTotal());
//            return ResponseResult.okResult(pageVo);
//        }
//
//        @Override
//        public ResponseResult addTag(Tag tag) {
//            if (!StringUtils.hasText(tag.getName())){
//                throw new SystemException(AppHttpCodeEnum.TAG_NAME);
//            }
//            save(tag);
//            return ResponseResult.okResult();
//        }
//
//
//        @Override
//        public ResponseResult deleteTag(Long id) {
//            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(Tag::getId,id);
//            remove(queryWrapper);
//            return ResponseResult.okResult();
//        }
//
//        @Override
//        public ResponseResult updateTagContent(Long id) {
//            //根据id查询
//            Tag tag = getById(id);
//            //封装
//            TagListVo tagListVo = BeanCopyUtils.copyBean(tag, TagListVo.class);
//            return ResponseResult.okResult(tagListVo);
//        }
//
//        @Override
//        public ResponseResult updateTag(Tag tag) {
//            updateById(tag);
//            return ResponseResult.okResult();
//        }
//
//        @Override
//        public List<TagVo> listTag() {
//            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.select(Tag::getId,Tag::getName);
//            List<Tag> list = list(queryWrapper);
//            List<TagVo> list1 = BeanCopyUtils.copyBeanList(list, TagVo.class);
//            return list1;
//        }
    }

