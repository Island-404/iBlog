package com.island.myblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.island.myblog.common.dto.*;
import com.island.myblog.common.vo.ArticleEditVo;
import com.island.myblog.common.vo.ConditionVO;
import com.island.myblog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据文章id获取文章信息
     * @param id 文章id
     * @return
     */
    ArticleDTO getArticleById(Integer id);

    /**
     * 保存或更新文章
     * @param articleEditVo
     * @return
     */
    int saveOrUpdateArticle(ArticleEditVo articleEditVo);

    /**
     * 查询首页文章
     *
     * @param current 当前页码
     * @return 文章
     */
    List<ArticleHomeDTO> listArticles(Integer current);

    /**
     * 查询文章归档
     *
     * @param current 当前页码
     * @return 文章
     */
    IPage listArchives(Integer current);

    /**
     * 查询文章根据条件
     *
     * @param condition 条件
     * @return 文章
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition);

    /**
     * 搜索文章根据条件
     *
     * @param condition 条件
     * @return 文章
     */
    List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition);

    /**
     * 点赞文章
     *
     * @param articleId 文章id
     */
    void saveArticleLike(Integer articleId);
}
