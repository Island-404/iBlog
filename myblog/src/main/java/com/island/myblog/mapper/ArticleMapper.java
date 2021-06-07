package com.island.myblog.mapper;

import com.island.myblog.common.dto.ArticleDTO;
import com.island.myblog.common.dto.ArticleHomeDTO;
import com.island.myblog.common.dto.ArticlePreviewDTO;
import com.island.myblog.common.dto.ArticleSearchDTO;
import com.island.myblog.common.vo.ConditionVO;
import com.island.myblog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Component
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据id获取文章信息
     *
     * @param id 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(Integer id);

    /**
     * 查询文章排行
     *
     * @param articleIdList
     * @return
     */
    List<Article> listArticleRank(@Param("articleIdList") List<Integer> articleIdList);

    /**
     * 查询首页文章
     *
     * @param current 当前页码
     * @return 首页文章集合
     */
    List<ArticleHomeDTO> listArticles(Integer current);

    /**
     * 查询文章根据条件
     *
     * @param condition 条件
     * @return 文章
     */
    List<ArticlePreviewDTO> listArticlesByCondition(@Param("condition") ConditionVO condition);

    /**
     * 搜索文章根据条件
     *
     * @param condition 条件
     * @return 文章
     */
    List<ArticleSearchDTO> listArticlesBySearch(@Param("condition") ConditionVO condition);
}
