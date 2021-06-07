package com.island.myblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.island.myblog.common.dto.ArticleHomeDTO;
import com.island.myblog.common.dto.ArticleSearchDTO;
import com.island.myblog.common.vo.ArticleEditVo;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.dto.ArticleDTO;
import com.island.myblog.common.vo.ConditionVO;
import com.island.myblog.entity.Article;
import com.island.myblog.service.ArticleService;
import com.island.myblog.service.TagsService;
import com.island.myblog.utils.QiniuCloudUtil;
import com.island.myblog.utils.ShiroUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@RestController

public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagsService tagsService;


    @ApiOperation(value = "后台文章列表")
    @GetMapping("/admin/articles")
    public Result listBackArticle(@RequestParam(value = "state",defaultValue = "-1") Integer state,
                       @RequestParam(value = "page",defaultValue = "1") Integer currentPage,
                       @RequestParam(value = "count",defaultValue = "6")Integer pageSize,
                       String keywords){

        Page page = new Page(currentPage,pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (state!=-1){
            wrapper.eq("state",state);

        }
        wrapper.like("title",keywords)
               .orderByDesc("createTime");
        IPage pageData = articleService.page(page, wrapper);
        return Result.succ(pageData);
    }

    @ApiOperation(value = "前台文章列表")
    @GetMapping("/articles")
    public Result listArticle(@RequestParam(value = "current",defaultValue = "1") Integer current){

        List<ArticleHomeDTO> articleHomeDTOList = articleService.listArticles(current);
        return Result.succ(articleHomeDTOList);
    }

    @ApiOperation(value = "文章细节")
    @GetMapping("/articles/{articleId}")
    public Result detail(@PathVariable Integer articleId){
        ArticleDTO article = articleService.getArticleById(articleId);
        Assert.notNull(article,"该博客已被删除");
        return Result.succ(article);
    }

    @ApiOperation(value = "分类列表")
    @GetMapping("/articles/archives")
    public Result getArchives(Integer current){
        IPage articlePageData = articleService.listArchives(current);
        return Result.succ("查询成功",articlePageData);
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public Result listArticleByKeywords(@RequestParam(value = "current",defaultValue = "1") Integer current,
                                        @RequestParam(value = "keywords") String keywords){

        ConditionVO condition = ConditionVO.builder()
                .keywords(keywords)
                .current(current)
                .build();
        List<ArticleSearchDTO> articleSearchDTOList = articleService.listArticlesBySearch(condition);
        return Result.succ("查询成功",articleSearchDTOList);
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "编辑文章")
    @PostMapping("/admin/articles")
    public Result edit(@Validated ArticleEditVo articleEditVo){
        //System.out.println(articleEditDTO.getId()+"a");
        Article temp = null;
        if (articleEditVo.getId()!=-1){
            temp = articleService.getById(articleEditVo.getId());
            //System.out.println(ShiroUtil.getProfile().getId()+temp.getUid()+"");
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUid() != ShiroUtil.getProfile().getId(),"没有权限编辑");
        }
        int res = articleService.saveOrUpdateArticle(articleEditVo);
        if (res>0){
            return Result.succ(articleEditVo.getId());
        } else {
            return Result.fail(articleEditVo.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }

    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "还原文章")
    @PutMapping("/admin/article/restore")
    public Result restoreArticle(@RequestParam("articleId") Integer id){
        System.out.println(id);
        Article article = new Article();
        article.setId(id);
        article.setState(1);
        boolean res = articleService.updateById(article);
        if (!res){
            return Result.fail("error","还原失败！");
        }

        return Result.succ("success","还原成功！");
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "删除文章")
    @PutMapping("/admin/article/dustbin")
    public Result dustbinArticle(@RequestParam("aids") List<Integer> aids,
                                 @RequestParam("state") Integer state){
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.in("id",aids);
        boolean res = false;
        if(state != 2){
            Article article = new Article();
            article.setState(2);
            res = articleService.update(article,wrapper);
        } else{
            res = articleService.remove(wrapper);
        }

        if (!res){
            return Result.fail("error","删除失败");
        }
        return Result.succ("success","删除成功");
    }

    @ApiOperation(value = "点赞文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @PostMapping("/articles/like")
    public Result saveArticleLike(Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.succ("点赞成功");
    }

    @ApiOperation(value = "上传文章图片")
    @ApiImplicitParam(name = "image", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/uploadimg")
    public Result uploadImg(MultipartFile image) {
        String url = QiniuCloudUtil.uploadImg(image);
        return Result.succ("上传成功",url);
    }
}

