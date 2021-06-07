package com.island.myblog.controller;


import com.island.myblog.common.dto.CommentsDTO;
import com.island.myblog.common.dto.PageDTO;
import com.island.myblog.common.dto.ReplyDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.vo.CommentsVo;
import com.island.myblog.service.CommentsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author island
 * @since 2021-05-28
 */
@RestController
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @ApiModelProperty("查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "current", value = "当前页码", required = true, dataType = "Integer")
    })
    @GetMapping("/comments")
    public Result listComments(Integer articleId, Integer current){
        PageDTO<CommentsDTO> pageDTO = commentsService.listComment(articleId, current);
        return Result.succ("查询成功",pageDTO);
    }

    @RequiresAuthentication
    @ApiOperation(value = "添加评论或回复")
    @PostMapping("/comments")
    public Result savaComments(@Validated @RequestBody CommentsVo commentsVo){
        int res = commentsService.savaComments(commentsVo);
        if (res!=1){
            return Result.fail("添加失败");
        }
        return Result.succ(200,"添加成功",null);
    }

    @ApiModelProperty("查询评论下回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "current", value = "当前回复页码", required = true, dataType = "Integer")
    })
    @GetMapping("/comments/replies/{commentId}")
    public Result listReplies(@PathVariable Integer commentId, Integer current){
        List<ReplyDTO> replyDTOList = commentsService.listReplyByCommentId(commentId, current);
        return Result.succ("查询成功",replyDTOList);
    }

}

