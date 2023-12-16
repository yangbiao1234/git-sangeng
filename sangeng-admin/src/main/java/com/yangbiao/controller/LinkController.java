package com.yangbiao.controller;

import com.yangbiao.annotation.SystemLog;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.dto.*;
import com.yangbiao.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //进入controller中的方法都是在响应体当中的
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult adminListLink(PageDto pageDto, QueryLinkDto queryLinkDto) {
        return linkService.adminListLink(pageDto,queryLinkDto);
    }

    @PostMapping
    public ResponseResult adminLink(@RequestBody LinkDto linkDto) {
        return linkService.adminLink(linkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult adminLinkGetById(@PathVariable Long id) {
        return linkService.adminLinkGetById(id);
    }

    @PutMapping
    public ResponseResult adminLinkPut(@RequestBody LinkPutDto linkPutDto) {
        return linkService.adminLinkPut(linkPutDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult adminLinkDelete(@PathVariable Long id) {
        return linkService.adminLinkDelete(id);
    }
}
