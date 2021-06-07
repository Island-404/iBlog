package com.island.myblog.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 用户信息vo
 *
 * @author 11921
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户信息对象")
public class UserInfoVO {

    /**
     * 用户简介
     */
    @ApiModelProperty(name = "intro", value = "介绍", dataType = "String")
    private String intro;
    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    /**
     * 用户邮箱
     */
    @Email
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(name = "email", value = "邮箱", dataType = "String")
    private String email;



}
