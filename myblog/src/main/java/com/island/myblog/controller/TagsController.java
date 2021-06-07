package com.island.myblog.controller;


import com.island.myblog.common.dto.ArticlePreviewListDTO;
import com.island.myblog.common.dto.PageDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.vo.ConditionVO;
import com.island.myblog.entity.Tags;
import com.island.myblog.service.ArticleService;
import com.island.myblog.service.TagsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@RestController
public class TagsController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagsService tagsService;

    @ApiOperation(value = "查看标签下对应的文章")
    @GetMapping("/tags/{tagId}")
    public Result listArticlesByCategoryId(@PathVariable("tagId") Integer tagId, Integer current) {
        ConditionVO conditionVO = ConditionVO.builder()
                .tagId(tagId)
                .current(current)
                .build();
        ArticlePreviewListDTO articlePreviewListDTO =  articleService.listArticlesByCondition(conditionVO);
        return Result.succ("查询成功",articlePreviewListDTO);
    }

    @GetMapping("/tags")
    public Result listTags(){
        PageDTO<Tags> pageDTO = tagsService.listTags();
        return Result.succ("查询成功",pageDTO);
    }
}

