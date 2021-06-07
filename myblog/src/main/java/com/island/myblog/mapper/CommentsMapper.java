package com.island.myblog.mapper;

import com.island.myblog.common.dto.CommentsDTO;
import com.island.myblog.common.dto.ReplyCountDTO;
import com.island.myblog.common.dto.ReplyDTO;
import com.island.myblog.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-28
 */
public interface CommentsMapper extends BaseMapper<Comments> {

    /**
     *
     * @param articleId 文章id
     * @param current   当前评论页数
     * @return  评论集合
     */
    public List<CommentsDTO> getCommentsDTOList(@Param("articleId") Integer articleId,
                                                @Param("current") Integer current);

    /**
     *
     * @param commentIdList 评论id列表
     * @return  回复集合
     */
    List<ReplyDTO> getReplyDTOList(@Param("commentIdList") List<Integer> commentIdList);

    /**
     *
     * @param commentIdList 评论id列表
     * @return 评论回复量
     */
    List<ReplyCountDTO> getReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查找回复列表
     *
     * @param commentId 评论id
     * @param current 当前页码
     * @return 回复列表
     */
    List<ReplyDTO> getReplyDTOListByCommentId(@Param("commentId") Integer commentId,
                                              @Param("current") Integer current);
}
