package com.yangbiao.controller;

import com.yangbiao.annotation.SystemLog;
import com.yangbiao.domain.ResponseResult;
import com.yangbiao.domain.entity.User;
import com.yangbiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 进入个人中心的时候需要能够查看当前用户信息
     * @return
     */
    @SystemLog(businessName = "进入个人中心查看当前用户信息")
    @GetMapping("/userInfo")
    public ResponseResult getAllLink() {
        return userService.userInfo();
    }

    /**
     *在编辑完个人资料后点击保存会对个人资料进行更新。
     */
    @SystemLog(businessName = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult userInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    /**
     * 要求用户能够在注册界面完成用户的注册。要求用户名，昵称，邮箱不能和数据库中原有的数据重复。
     * 如果某项重复了注册失败并且要有对应的提示。
     * 并且要求用户名，密码，昵称，邮箱都不能为空。
     * ​注意:密码必须密文存储到数据库中。
     */
    @SystemLog(businessName = "注册用户")
    @PostMapping("/register")
    public ResponseResult register(@Validated User user, BindingResult bindingResult){
        // 参数校验
        if (bindingResult.hasErrors()) {
            String messages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .reduce((m1, m2) -> m1 + "；" + m2)
                    .orElse("参数输入有误！");
            //这里可以抛出自定义异常,或者进行其他操作
            throw new IllegalArgumentException(messages);
        }
        return userService.register(user);
    }

}
