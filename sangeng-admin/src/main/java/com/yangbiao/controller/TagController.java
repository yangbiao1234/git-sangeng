package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.AddTagDto;
import com.yangbiao.domain.dto.TagListDto;
import com.yangbiao.domain.entity.Tag;
import com.yangbiao.domain.vo.PageVo;
import com.yangbiao.service.TagService;
import com.yangbiao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 为了方便后期对文章进行管理，需要提供标签的功能，一个文章可以有多个标签。
     * 在后台需要分页查询标签功能，要求能根据标签名进行分页查询。 **后期可能会增加备注查询等需求**。
     * 注意：不能把删除了的标签查询出来。
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    /**
     * 点击标签管理的新增按钮可以实现新增标签的功能。
     * 自动填充对应字段 P-49
     * @param tagDto
     * @return
     */
    @PostMapping
    public ResponseResult addTag(@RequestBody AddTagDto tagDto){
        return tagService.addTag(tagDto);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseResult delete(@PathVariable Long id){
//        tagService.removeById(id);
//        return ResponseResult.okResult();
//    }
//
//    @PutMapping
//    public ResponseResult edit(@RequestBody EditTagDto tagDto){
//        Tag tag = BeanCopyUtils.copyBean(tagDto,Tag.class);
//        tagService.updateById(tag);
//        return ResponseResult.okResult();
//    }
//
//
//    @GetMapping(value = "/{id}")
//    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
//        Tag tag = tagService.getById(id);
//        return ResponseResult.okResult(tag);
//    }
//
//
//    @GetMapping("/listAllTag")
//    public ResponseResult listAllTag(){
//        List<TagVo> list = tagService.listAllTag();
//        return ResponseResult.okResult(list);
//    }
}

