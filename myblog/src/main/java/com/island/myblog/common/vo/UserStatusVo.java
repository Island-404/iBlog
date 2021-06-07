package com.island.myblog.common.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserStatusVo对象", description="")
public class UserStatusVo implements Serializable {

    // 是否为开启状态
    private Boolean enabled;

    // 用户id
    private Integer uid;
}
