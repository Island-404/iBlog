package com.island.myblog.service;

import com.island.myblog.common.dto.BlogBackInfoDTO;
import com.island.myblog.common.dto.BlogHomeInfoDTO;

public interface BlogInfoService {

    /**
     * 获取首页数据
     * @return 博客信息
     */
    BlogHomeInfoDTO getBlogInfo();
    /**
     * 获取后台首页数据
     * @return 博客后台信息
     */
    BlogBackInfoDTO getBlogBackInfo();

    /**
     * 获取关于我内容
     * @return 关于我内容
     */
    String getAbout();

    /**
     * 修改关于我内容
     * @param aboutContent 关于我内容
     */
    void updateAbout(String aboutContent);

    /**
     * 更新公告
     * @param notice
     */
    void updateNotice(String notice);

    /**
     * 获取公告
     * @return 公告
     */
    Object getNotice();
}
