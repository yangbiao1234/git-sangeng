package com.yangbiao.service;

import com.yangbiao.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * 在个人中心点击编辑的时候可以上传头像图片。上传完头像后，可以用于更新个人信息接口。
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
