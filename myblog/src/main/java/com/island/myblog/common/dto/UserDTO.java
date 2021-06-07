package com.island.myblog.common.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.island.myblog.entity.Roles;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.management.relation.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserDTO对象", description="")
public class UserDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String nickname;

    /**
     * 自我介绍
     */
    private String intro;

    private String password;

    private Boolean enabled;

    @NotBlank(message = "不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String userface;

    @TableField(value = "createTime",fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    private List<Role> roles;
}
