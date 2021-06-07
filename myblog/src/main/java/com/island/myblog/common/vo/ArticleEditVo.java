package com.island.myblog.common.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleEditDTO对象", description="")
public class ArticleEditVo implements Serializable {


    private Integer id;

    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "md文件源码")
    @TableField("mdContent")
    @NotBlank(message = "内容不能为空")
    private String mdContent;

    @ApiModelProperty(value = "html源码")
    @TableField("htmlContent")
    private String htmlContent;

    private Integer cid;

    @ApiModelProperty(value = "0表示草稿箱，1表示已发表，2表示已删除")
    private Integer state;

    private String[] dynamicTags;

}
