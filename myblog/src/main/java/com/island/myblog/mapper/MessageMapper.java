package com.island.myblog.mapper;

import com.island.myblog.common.dto.MessageDTO;
import com.island.myblog.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-29
 */
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 获取留言列表
     *
     * @return 留言列表
     */
    List<MessageDTO> listMessageDTO();
}
