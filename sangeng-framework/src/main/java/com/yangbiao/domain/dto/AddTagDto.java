package com.yangbiao.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //创建人  自动填充
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}
