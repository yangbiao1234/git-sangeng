package com.yangbiao.controller;


import com.yangbiao.annotation.SystemLog;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;
import com.yangbiao.enums.AppHttpCodeEnum;
import com.yangbiao.exception.SystemException;
import com.yangbiao.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    /**
     * 需要实现登录功能
     * ​有些功能必须登录后才能使用，未登录状态是不能使用的。
     * @param user
     * @return
     */
    @SystemLog(businessName = "登录功能")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示必须要输入用户名  自定义异常
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    /**
     *要实现的操作：
     *删除redis中的用户信息
     * @return
     */
    @SystemLog(businessName = "退出登录")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
