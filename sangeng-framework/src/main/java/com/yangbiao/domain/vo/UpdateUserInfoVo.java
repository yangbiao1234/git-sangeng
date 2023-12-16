package com.yangbiao.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserInfoVo {
    //主键，禁用雪花算法，使用mysql的主键自增策略
    private Long id;
    //用户名
    private String userName;
    //邮箱
    private String email;
    //昵称
    private String nickName;
    //手机号
    private String sex;
    //账号状态（0正常 1停用）
    private Integer status;

}