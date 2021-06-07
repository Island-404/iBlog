package com.island.myblog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 博客后台信息
 *
 * @author 11921
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoDTO {

    /**
     * 博主头像
     */
    private String userface;

    /**
     * 博主昵称
     */
    private String nickname;

    /**
     * 博主介绍
     */
    private String intro;

    /**
     * 文章量
     */
    private Integer articleCount;

    /**
     * 分类统计
     */
    private Integer categoryCount;

    /**
     * 标签统计
     */
    private Integer tagCount;

    /**
     * 公告
     */
    private String notice;

    /**
     * 访问量
     */
    private String viewsCount;

    /**
     * 文章浏览量排行
     */
    private List<ArticleRankDTO> articleRankDTOList;


}
