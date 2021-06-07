package com.island.myblog.controller;


import com.island.myblog.common.dto.MessageDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.vo.MessageVo;
import com.island.myblog.entity.Message;
import com.island.myblog.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author island
 * @since 2021-05-29
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation("查询留言列表")
    @GetMapping("/messages")
    public Result listMessage(){
        List<MessageDTO> messageDTOList = messageService.listMessageDTO();
        return Result.succ("查询成功",messageDTOList);
    }

    @ApiOperation("添加留言")
    @PostMapping("/messages")
    public Result saveMessage(@Valid @RequestBody MessageVo messageVo){
        messageService.saveMessage(messageVo);
        return Result.succ("添加成功");
    }
}

