package com.yangbiao.domain.entity;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2023-05-30 20:32:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("sg_article")
//返回这个当前对象本身
@Accessors(chain = true)
public class Article {

    @TableId
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    //所属分类名称
    @TableField(exist = false)
    private String categoryName;

    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;

    /**
     * 以下四个属性为数据可自定义填充对象
     */
    //创建者
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更改者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //更改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    //这是Redis定时任务访问数据库的构造方法
    public Article(Long id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }

    @TableField(exist = false) //实体类的属性在数据库表中不存在
    private List<Long> tags;
}

