package com.yangbiao.controller;


import com.yangbiao.domain.ResponseResult;
import com.yangbiao.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * MultipartFile是一个用于处理HTTP请求中的文件上传的接口或类，通常在Web开发中使用。
     * 它允许将文件从客户端发送到服务器，并提供一些方法来访问文件的属性和内容。
     * @param img
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}

