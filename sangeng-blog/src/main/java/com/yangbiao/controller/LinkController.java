package com.yangbiao.controller;

import com.yangbiao.annotation.SystemLog;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //进入controller中的方法都是在响应体当中的
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 在友链页面要查询出所有的审核通过的友链。
     * @return
     */
    @SystemLog(businessName = "友链查询")
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink() {

        return linkService.getAllLink();
    }
}
