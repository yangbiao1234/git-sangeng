package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.TagListDto;
import com.yangbiao.domain.vo.PageVo;
import com.yangbiao.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

//    @PostMapping
//    public ResponseResult add(@RequestBody AddTagDto tagDto){
//        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
//        tagService.save(tag);
//        return ResponseResult.okResult();
//    }
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

