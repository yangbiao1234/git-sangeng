package com.yangbiao.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTagDto {
    @TableId
    private Long id;

    //标签名
    private String name;

    //备注
    private String remark;
}
