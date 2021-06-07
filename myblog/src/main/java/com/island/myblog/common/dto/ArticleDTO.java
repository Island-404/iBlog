package com.island.myblog.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.island.myblog.entity.Tags;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO implements Serializable {
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

    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;

    @ApiModelProperty(value = "0表示草稿箱，1表示已发表，2表示已删除")
    private Integer state;

    /**
     * 浏览量
     */
    @TableField("pageView")
    private Integer pageView;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 标签
     */
    private List<Tags> tags;


}
