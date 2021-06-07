package com.island.myblog.common.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 查询条件
 *
 * @author island
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "留言信息")
public class MessageVo {

    @ApiModelProperty(name = "userface",value = "用户头像",required = true,dataType = "String")
    private String userface;

    @ApiModelProperty(name = "nickname",value = "用户昵称",required = true,dataType = "String")
    private String nickname;

    @NotNull(message = "内容不能为空")
    @ApiModelProperty(name = "messageContent",value = "留言信息",required = true,dataType = "String")
    private String messageContent;

    @ApiModelProperty(name = "time",value = "弹幕速度",required = true,dataType = "Integer")
    private Integer time;
}
