package com.island.myblog.common.dto;

import com.island.myblog.entity.Tags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 分类或标签下的文章
 * @author 11921
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePreviewDTO {

    /**
     * 文章id
     */
    private Integer id;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 文章概况
     */
    private String summary;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 作者名
     */
    private String nickname;

    /**
     * 文章分类id
     */
    private Integer categoryId;

    /**
     * 文章分类名
     */
    private String cateName;

    /**
     * 文章标签
     */
    private List<Tags> tags;


}
