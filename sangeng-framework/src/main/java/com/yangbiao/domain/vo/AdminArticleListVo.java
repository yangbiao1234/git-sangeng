package com.yangbiao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminArticleListVo {
    //文章标题
    private String title;
    //文章摘要
    private String summary;
}
