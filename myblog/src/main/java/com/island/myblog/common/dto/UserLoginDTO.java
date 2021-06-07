package com.island.myblog.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements Serializable {


    /**
     * 用户账号id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 自我介绍
     */
    private String intro;

    /**
     * 用户头像
     */
    private String userface;

    /**
     * 点赞文章集合
     */
    private Set<Integer> articleLikeSet;

}
