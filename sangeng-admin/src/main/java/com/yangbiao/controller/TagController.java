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
     * 新增标签
     * 点击标签管理的新增按钮可以实现新增标签的功能。
     * 自动填充对应字段 P-49
     * @param tagDto
     * @return
     */
    @PostMapping
    public ResponseResult addTag(@RequestBody AddTagDto tagDto){
        return tagService.addTag(tagDto);
    }

    /**
     * 删除标签
     * 路径：content/tag/6   代表删除id为6的标签数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    /**
     * 修改标签第一步点击修改标签查询到要修改的标签
     * 获取标签信息
     * 路径：content/tag/6   代表获取id为6的标签数据
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseResult getTag(@PathVariable(value = "id")Long id){
        return tagService.getTag(id);
    }

    /**
     * 修改标签第二部根据id修改标签
     * 修改标签接口
     * @param tagDto
     * @return
     */
    @PutMapping
    public ResponseResult updateTag(@RequestBody AddTagDto tagDto){
        return tagService.updateTagContent(tagDto);
    }

//
//
//    @GetMapping("/listAllTag")
//    public ResponseResult listAllTag(){
//        List<TagVo> list = tagService.listAllTag();
//        return ResponseResult.okResult(list);
//    }
}

