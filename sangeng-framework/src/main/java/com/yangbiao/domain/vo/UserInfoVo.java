package com.yangbiao.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;


}
