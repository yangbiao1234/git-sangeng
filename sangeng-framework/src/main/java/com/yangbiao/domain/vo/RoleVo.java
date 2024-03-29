package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {
    //角色ID@TableId
    private Long id;
    //角色权限字符串
    private String roleKey;
    //角色名称
    private String roleName;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
}
