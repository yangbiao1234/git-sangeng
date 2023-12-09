package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeSelectAllVo {

    private List<MenuTreeSelectVo> menus;

    private List<Long> checkedKeys;
}
