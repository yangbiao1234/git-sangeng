package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo1 {
    //角色ID@TableId
    private Long id;
    //角色权限字符串
    private String roleKey;
    //角色名称
    private String roleName;
    //显示顺序
    private Long roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //备注
    private String remark;
    //删除标志（0代表存在 1代表删除）
    private Integer delFlag;
    //创建者
    private Long createBy;
    //创建时间
    private Date createTime;
    //更改者
    private Long updateBy;

}
