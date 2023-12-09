package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectVo {

    //菜单树
    private List<MenuTreeSelectVo> children;

    private Long id;

    //菜单名称
    private String label;

    //父菜单id
    private Long parentId;
}
