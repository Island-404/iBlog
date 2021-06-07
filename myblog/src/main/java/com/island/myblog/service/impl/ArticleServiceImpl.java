package com.island.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.island.myblog.common.dto.*;
import com.island.myblog.common.vo.ArticleEditVo;
import com.island.myblog.common.vo.ConditionVO;
import com.island.myblog.entity.Article;
import com.island.myblog.entity.Category;
import com.island.myblog.entity.Tags;
import com.island.myblog.mapper.ArticleMapper;
import com.island.myblog.mapper.CategoryMapper;
import com.island.myblog.mapper.TagsMapper;
import com.island.myblog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.island.myblog.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.island.myblog.common.constant.CommonConst.DEFAULT_PAGESIZE;
import static com.island.myblog.common.constant.RedisPrefixConst.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Qualifier("articleMapper")
    private ArticleMapper articleMapper;
    @Autowired
    @Qualifier("tagsMapper")
    private TagsMapper tagsMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ArticleDTO getArticleById(Integer id) {
        redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).increment(id.toString(), 1);
        ArticleDTO articleDTO = articleMapper.getArticleById(id);
        articleDTO.setPageView((Integer) redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).get(id.toString()));
        articleDTO.setLikeCount((Integer) redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).get(id.toString()));
        return articleDTO;
    }


    @Override
    public int saveOrUpdateArticle(ArticleEditVo articleEditVo) {
        Article article = new Article();
        article.setId(articleEditVo.getId());
        article.setTitle(articleEditVo.getTitle());
        article.setMdContent(articleEditVo.getMdContent());
        article.setHtmlContent(articleEditVo.getHtmlContent());
        article.setCid(articleEditVo.getCid());
        article.setState(articleEditVo.getState());
        //处理文章摘要
        String stripHtml = stripHtml(articleEditVo.getHtmlContent());
        article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));

        int res;
        // 文章不存在就insert
        if(articleEditVo.getId() == -1){
            //设置当前用户
            article.setUid(ShiroUtil.getProfile().getId());
            res = articleMapper.insert(article);
        } else{ //文件存在就update
            res = articleMapper.updateById(article);
        }

        String[] dynamicTags = articleEditVo.getDynamicTags();
        if (dynamicTags != null && dynamicTags.length > 0) {
            int tags = addTagsToArticle(dynamicTags, article.getId());
            if (tags == -1) {
                return tags;
            }
        }
        return res;
    }

    @Override
    public List<ArticleHomeDTO> listArticles(Integer current) {
        // 转换页码分页查询文章
        List<ArticleHomeDTO> articleDTOList = articleMapper.listArticles((current - 1) * 10);
        return articleDTOList;
    }

    @Override
    public IPage listArchives(Integer current) {
        Page articlePage = new Page(current,DEFAULT_PAGESIZE);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        wrapper.select("id","title","createTime")
               .eq("state", 1)
               .orderByDesc("createTime");
        IPage articlePageData = articleMapper.selectMapsPage(articlePage,wrapper);
        return articlePageData;
    }

    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition) {
        // 转换页码
        condition.setCurrent((condition.getCurrent() - 1) * 9);
        // 搜索条件对应数据
        List<ArticlePreviewDTO> articlePreviewDTOList = articleMapper.listArticlesByCondition(condition);
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCateName)
                    .eq(Category::getId, condition.getCategoryId()))
                    .getCateName();
        } else {
            name = tagsMapper.selectOne(new LambdaQueryWrapper<Tags>()
                    .select(Tags::getTagName)
                    .eq(Tags::getId, condition.getTagId()))
                    .getTagName();
        }

        return ArticlePreviewListDTO.builder()
                .articlePreviewDTOList(articlePreviewDTOList)
                .name(name)
                .build();

    }

    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition) {
        // 转换页码
        condition.setCurrent((condition.getCurrent() - 1) * 9);
        List<ArticleSearchDTO> articleSearchDTOList = articleMapper.listArticlesBySearch(condition);

        return articleSearchDTOList;
    }

    @Transactional
    @Override
    public void saveArticleLike(Integer articleId) {
        // 查询当前用户点赞过的文章id集合
        Set<Integer> articleLikeSet = (Set<Integer>) redisTemplate.boundHashOps(ARTICLE_USER_LIKE).get(ShiroUtil.getProfile().getId().toString());
        // 第一次点赞则创建
        if (CollectionUtils.isEmpty(articleLikeSet)) {
            articleLikeSet = new HashSet<>();
        }
        // 判断是否点赞
        if (articleLikeSet.contains(articleId)) {
            // 点过赞则删除文章id
            articleLikeSet.remove(articleId);
            // 文章点赞量-1
            redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).increment(articleId.toString(), -1);
        } else {
            // 未点赞则增加文章id
            articleLikeSet.add(articleId);
            // 文章点赞量+1
            redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).increment(articleId.toString(), 1);
        }
        // 保存点赞记录
        redisTemplate.boundHashOps(ARTICLE_USER_LIKE).put(ShiroUtil.getProfile().getId().toString(), articleLikeSet);
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    private int addTagsToArticle(String[] dynamicTags, Integer aid) {
        //1.删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Integer> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsMapper.saveTagsArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }

}
