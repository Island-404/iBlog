package com.island.myblog.common.dto;

import com.island.myblog.entity.Tags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 首页文章列表
 * @author 11921
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHomeDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String mdContent;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 文章分类id
     */
    private Integer cid;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 文章分类名
     */
    private String cateName;

    /**
     * 文章标签
     */
    private List<Tags> tags;

}
