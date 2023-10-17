package com.yangbiao.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
//返回这个当前对象本身
//@Accessors(chain = true)
public class TagListDto {

    //name：标签名
    private String name;

    //remark：备注
    private String remark;
}
