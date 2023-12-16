package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserVo {
    private UpdateUserInfoVo user;
    private List<Long> roleIds;
    private List<SimpleRoleVo> roles;
}
