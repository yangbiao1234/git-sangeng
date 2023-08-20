package com.yangbiao.domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-06-18 16:40:38
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User  {
    //主键@TableId
    private Long id;

    //用户名
    @NotBlank(message = "姓名不能为空")
    @Length(min = 2,max = 20,message = "名字长度2-20")
    private String userName;

    //昵称
    @NotBlank(message = "昵称不能为空")
    @Length(min = 1,max = 50,message = "名字长度1-50")
    private String nickName;

    //密码
    @NotEmpty(message = "密码不能为空")
    @Length(min = 3, max = 20, message = "密码长度为3-20位。")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "密码不合法") //[a-zA-Z]*
    private String password;

    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;

    //邮箱
    @NotBlank(message = "邮箱不能为空")
    @Length(min = 4,max = 32,message = "邮箱长度4-32")
    private String email;

    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}

