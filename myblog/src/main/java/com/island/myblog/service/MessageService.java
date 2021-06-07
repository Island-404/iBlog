package com.island.myblog.service;

import com.island.myblog.common.dto.MessageDTO;
import com.island.myblog.common.vo.MessageVo;
import com.island.myblog.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-29
 */
public interface MessageService extends IService<Message> {

    /**
     *
     * @return MessageDTO
     */
    List<MessageDTO> listMessageDTO();

    /**
     *
     * @param messageVo 留言信息
     * @return
     */
    int saveMessage(MessageVo messageVo);

}
