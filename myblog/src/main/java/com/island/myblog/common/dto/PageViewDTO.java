package com.island.myblog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 访问量DTO
 * @author: island
 * @date: 2021-01-16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageViewDTO implements Serializable {

    /**
     * 日期
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewsCount;

}
