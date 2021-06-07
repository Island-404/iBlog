package com.island.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.island.myblog.common.dto.MessageDTO;
import com.island.myblog.common.vo.MessageVo;
import com.island.myblog.entity.Message;
import com.island.myblog.mapper.MessageMapper;
import com.island.myblog.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-29
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;


    @Override
    public List<MessageDTO> listMessageDTO() {

        List<MessageDTO> messageDTOList = messageMapper.listMessageDTO();
        return messageDTOList;
    }

    @Override
    public int saveMessage(MessageVo messageVo) {
        Message message = Message.builder()
                .nickname(messageVo.getNickname())
                .userface(messageVo.getUserface())
                .messageContent(messageVo.getMessageContent())
                .speed(messageVo.getTime())
                .build();
        int res = messageMapper.insert(message);
        return res;
    }
}
