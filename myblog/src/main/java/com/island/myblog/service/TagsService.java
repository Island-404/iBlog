package com.island.myblog.service;

import com.island.myblog.common.dto.PageDTO;
import com.island.myblog.entity.Tags;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
public interface TagsService extends IService<Tags> {

    /**
     *
     * @return 标签列表
     */
    PageDTO<Tags> listTags();
}
