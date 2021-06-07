package com.island.myblog.common.vo;

import com.island.myblog.entity.Roles;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserRolesVo对象", description="")
public class UserRolesVo implements Serializable {

    /**
     * 用户角色id数组
     */
    private Integer[] rids;

    /**
     * 用户id
     */
    private Integer uid;
}
