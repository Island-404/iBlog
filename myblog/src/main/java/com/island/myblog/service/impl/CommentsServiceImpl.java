package com.island.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.island.myblog.common.dto.CommentsDTO;
import com.island.myblog.common.dto.PageDTO;
import com.island.myblog.common.dto.ReplyCountDTO;
import com.island.myblog.common.dto.ReplyDTO;
import com.island.myblog.common.vo.CommentsVo;
import com.island.myblog.entity.Comments;
import com.island.myblog.mapper.CommentsMapper;
import com.island.myblog.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.island.myblog.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-28
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Transactional
    @Override
    public PageDTO<CommentsDTO> listComment(Integer articleId, Integer current) {
        // 查询文章评论量
        Integer commentCount = commentsMapper.selectCount(new LambdaQueryWrapper<Comments>()
                .eq(Objects.nonNull(articleId), Comments::getAid, articleId)
                .isNull(Objects.isNull(articleId), Comments::getAid)
                .isNull(Comments::getParentId));
        if (commentCount==0){
            return new PageDTO<>();
        }

        // 分页查询评论集合
        List<CommentsDTO> commentDTOList = commentsMapper.getCommentsDTOList(articleId, (current - 1) * 10);
        // 提取评论id集合
        List<Integer> commentIdList = new ArrayList<>();
        commentDTOList.forEach(item -> {
            commentIdList.add(item.getId());
        });
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentsMapper.getReplyDTOList(commentIdList);

        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream().collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = commentsMapper.getReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 将分页回复数据和回复量封装进对应的评论
        commentDTOList.forEach(item -> {
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });

        return new PageDTO<>(commentDTOList, commentCount);
    }

    @Override
    public int savaComments(CommentsVo commentsVo) {
        Comments comments = Comments.builder()
                .uid(ShiroUtil.getProfile().getId())
                .aid(commentsVo.getArticleId())
                .content(commentsVo.getCommentContent())
                .parentId(commentsVo.getParentId())
                .replyId(commentsVo.getReplyId())
                .build();

        return commentsMapper.insert(comments);
    }

    @Override
    public List<ReplyDTO> listReplyByCommentId(Integer commentId, Integer current) {

        List<ReplyDTO> replyDTOList = commentsMapper.getReplyDTOListByCommentId(commentId, (current - 1) * 5);

        return replyDTOList;
    }
}
