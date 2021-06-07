package com.island.myblog.service;

import com.island.myblog.common.dto.CommentsDTO;
import com.island.myblog.common.dto.PageDTO;
import com.island.myblog.common.dto.ReplyDTO;
import com.island.myblog.common.vo.CommentsVo;
import com.island.myblog.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-28
 */
public interface CommentsService extends IService<Comments> {

    /**
     *
     * @param articleId 文章id
     * @param cuurent   当前评论页数
     * @return
     */
    PageDTO<CommentsDTO> listComment(Integer articleId, Integer cuurent);
    /**
     *
     * @param commentsVo 评论信息
     * @return
     */
    int savaComments(CommentsVo commentsVo);

    /**
     *
     * @param commentId 评论id
     * @param current   当前回复页码
     * @return 回复列表
     */
    List<ReplyDTO> listReplyByCommentId(Integer commentId , Integer current);
}
