package com.yangbiao.controller;

import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;
import com.yangbiao.service.LinkService;
import com.yangbiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 进入个人中心的时候需要能够查看当前用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseResult getAllLink() {

        return userService.userInfo();
    }

    /**
     *在编辑完个人资料后点击保存会对个人资料进行更新。
     */
    @PostMapping("/userInfo")
    public ResponseResult userInfo(User user){
        return userService.updateUserInfo(user);
    }

}
