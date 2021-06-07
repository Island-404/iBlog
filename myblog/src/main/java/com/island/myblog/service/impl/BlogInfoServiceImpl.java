package com.island.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.island.myblog.common.dto.*;
import com.island.myblog.entity.Article;
import com.island.myblog.entity.Pv;
import com.island.myblog.entity.User;
import com.island.myblog.mapper.*;
import com.island.myblog.service.BlogInfoService;
import com.island.myblog.service.PvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.island.myblog.common.constant.CommonConst.BLOGGER_ID;
import static com.island.myblog.common.constant.CommonConst.DEFAULT_NOTICE;
import static com.island.myblog.common.constant.RedisPrefixConst.*;


/**
 * @author xiaojie
 * @since 2020-05-18
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private PvService pvService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public BlogHomeInfoDTO getBlogInfo() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.select("userface", "nickname", "intro")
                .eq("id",BLOGGER_ID);
        //查询博主信息
        User user = userMapper.selectOne(wrapper);
        // 查询文章数量
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getState, 1)
                );
        // 查询分类数量
        Integer categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagsMapper.selectCount(null);
        // 查询公告
        Object value = redisTemplate.boundValueOps(NOTICE).get();
        String notice = Objects.nonNull(value) ? value.toString() : "发布你的第一篇公告吧";
        // 查询访问量
        String viewsCount = Objects.requireNonNull(redisTemplate.boundValueOps(BLOG_VIEWS_COUNT).get()).toString();

        // 获取访问量前五的文章
        List<ArticleRankDTO> articleRankDTOList = this.getTopFiveArticle();

        // 封装数据
        return BlogHomeInfoDTO.builder()
                .nickname(user.getNickname())
                .userface(user.getUserface())
                .intro(user.getIntro())
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .notice(notice)
                .viewsCount(viewsCount)
                .articleRankDTOList(articleRankDTOList)
                .build();
    }

    @Transactional
    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        // 查询访问量
        Integer viewsCount = (Integer) redisTemplate.boundValueOps(BLOG_VIEWS_COUNT).get();
        // 查询留言量
        Integer messageCount = messageMapper.selectCount(null);
        // 查询用户量
        Integer userCount = userMapper.selectCount(null);
        // 查询文章量
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getState, 1)
                );
        // 查询一周用户量
        List<PageViewDTO> pvList = pvService.listPageViews();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryMapper.listCategoryDTO();
        // 获取访问量前五的文章
        List<ArticleRankDTO> articleRankDTOList = this.getTopFiveArticle();
        return BlogBackInfoDTO.builder()
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .pvList(pvList)
                .articleRankDTOList(articleRankDTOList)
                .build();
    }

    @Override
    public String getAbout() {
        Object value = redisTemplate.boundValueOps(ABOUT).get();
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAbout(String aboutContent) {
        redisTemplate.boundValueOps(ABOUT).set(aboutContent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNotice(String notice) {
        redisTemplate.boundValueOps(NOTICE).set(notice);
    }

    @Override
    public String getNotice() {
        Object value = redisTemplate.boundValueOps(NOTICE).get();
        return Objects.nonNull(value) ? value.toString() : "发布你的第一篇公告吧";
    }

    public List<ArticleRankDTO> getTopFiveArticle(){
        // 查询redis访问量前五的文章
        Map<String, Integer> articleViewsMap = redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).entries();
        // 将文章进行倒序排序
        List<Integer> articleIdList = Objects.requireNonNull(articleViewsMap).entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(item -> Integer.valueOf(item.getKey()))
                .collect(Collectors.toList());
        // 提取前五篇文章
        int index = Math.min(articleIdList.size(), 5);
        articleIdList = articleIdList.subList(0, index);
        // 文章为空直接返回
        if (articleIdList.isEmpty()) {
            return null;
        }
        // 查询文章标题
        List<Article> articleList = articleMapper.listArticleRank(articleIdList);
        // 封装浏览量
        List<ArticleRankDTO> articleRankDTOList = articleList.stream().map(article -> ArticleRankDTO.builder()
                .id(article.getId())
                .articleTitle(article.getTitle())
                .viewsCount(articleViewsMap.get(article.getId().toString()))
                .build())
                .collect(Collectors.toList());
        return articleRankDTOList;
    }
}
