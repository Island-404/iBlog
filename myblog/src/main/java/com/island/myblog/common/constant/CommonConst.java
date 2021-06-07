package com.island.myblog.common.constant;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * 公共常量
 *
 * @author: island
 * @date: 2021-05-29
 **/
public class CommonConst {

    /**
     * 否
     */
    public static final int FALSE = 0;

    /**
     * 是
     */
    public static final int TURE = 1;

    /**
     * 博主id
     */
    public static final int BLOGGER_ID = 7;

    /**
     * 博主id
     */
    public static final int DEFAULT_PAGESIZE = 10;

    /**
     * 默认状态
     */
    public static final boolean DEFAULT_ENABLE = true;

    /**
     * 默认用户昵称
     */
    public static final String DEFAULT_NICKNAME = "用户" + IdWorker.getId();

    /**
     * 默认用户昵称
     */
    public static final String DEFAULT_NOTICE = "开发阶段，解决BUG中！";

    /**
     * 默认用户头像
     */
    public static final String DEFAULT_AVATAR = "https://www.static.talkxj.com/avatar/user.png";


    /**
     * 网站域名
     */
    public static final String URL = "https://www.talkxj.com";

    /**
     * 文章页面路径
     */
    public static final String ARTICLE_PATH = "/articles/";


}
