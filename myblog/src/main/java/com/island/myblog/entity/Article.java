package com.island.myblog.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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

    @NotBlank(message = "概况不能为空")
    private String summary;

    private Integer cid;

    private Integer uid;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "0表示草稿箱，1表示已发表，2表示已删除")
    private Integer state;

    @TableField("pageView")
    private Integer pageView;



}
