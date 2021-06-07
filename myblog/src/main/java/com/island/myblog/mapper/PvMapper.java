package com.island.myblog.mapper;

import com.island.myblog.common.dto.PageViewDTO;
import com.island.myblog.entity.Pv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-23
 */

@Repository
public interface PvMapper extends BaseMapper<Pv> {

    /**
     * 获取一段时间内的浏览数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 浏览数据
     */
    List<PageViewDTO> listPageViews(@Param("startTime") String startTime,
                                    @Param("endTime") String endTime);
}
