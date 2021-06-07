package com.island.myblog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章排行
 *
 * @author 11921
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章概况
     */
    private String summary;



}
