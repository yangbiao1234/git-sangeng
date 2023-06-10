package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    //返回给前段的字段

    //id
    private Long id;

    //标题
    private String title;

    //访问量
    private Long viewCount;

}
