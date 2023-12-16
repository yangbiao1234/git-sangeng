package com.yangbiao.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryListVo {
    private Long id;
    private String name;
    private Integer status;
    private String description;
}