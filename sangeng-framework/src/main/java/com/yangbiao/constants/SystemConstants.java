package com.yangbiao.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  sg_category表中文章是状态是正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     *  sys_role 角色权限字符串
     */
    public static final String ROLE_PERMISSION = "admin";

    /**
     *LINK_STATUS_NORMAL 常量名大小写快捷键”CTRL + SHIFT + U“
     * 友链状态为审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     *  显示的页数
     */
    public static final long PAG1 = 1;

    /**
     *评论类型（0代表文章评论，1代表友链评论）
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     *评论类型（0代表文章评论，1代表友链评论）
     */
    public static final String LINK_COMMENT = "1";


    /**
     *  每页显示的数据
     */
    public static long PAG10 = 10;

    /**
     *  根评论 rootId为-1
     */
    public static long ROOTID = -1;

    /**
     *  2023/8/1
     *  七牛云30天测试域名
     */
    public static String HTTPURL = "http://rz4p9h55w.hb-bkt.clouddn.com";

    /**
     *  更新redis中 id的浏览量
     */
    public static String REDISKEY = "article:viewCount";

    /**
     *  更新redis中 id的浏览量
     */
    public static final String MENU = "C";

    /**
     *  更新redis中 id的浏览量
     */
    public static final String BUTTON = "F";

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";


    /**
     * 后台管理员用户
     */
    public static final String ADMAIN = "1";
    /**
     * 菜单权限表父菜单Id
     */
    public static final String PARENT_ID = "0";

    /**
     * 菜单权限表父菜单Id
     */
    public static final Long PARENT_ID_LONG = 0L;

    /**
     * 角色状态正常
     */
    public static final String ROLE_STATUS_NORMAL = "0";


}