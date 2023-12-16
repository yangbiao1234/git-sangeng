package com.yangbiao.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class PageDto {
    @Range(min = 1, max = 100, message = "最多查询至第100页")
    private Integer pageNum;
    @Range(min = 1, max = 20, message = "每页最多20条")
    private Integer pageSize;
}
