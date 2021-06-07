package com.island.myblog.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CategoryDTO对象", description="")
public class CategoryDTO implements Serializable {

    private String id;

    @TableField("cateName")
    private String cateName;

    /**
     * 分类下的文章数量
     */
    private Integer articleCount;

}
