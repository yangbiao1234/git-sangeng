package com.yangbiao.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryCategoryDto {
    //分类名
    private String name;

    //状态0:正常,1禁用
    private Integer status;
}
