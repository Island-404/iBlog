package com.island.myblog.service;

import com.island.myblog.common.dto.PageViewDTO;
import com.island.myblog.entity.Pv;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-23
 */
public interface PvService extends IService<Pv> {

    /**
     * 保存前一天浏览量
     */
    void saveUniqueView();


    /**
     * 获取浏览量表
     * @return
     */
    List<PageViewDTO> listPageViews();
}
