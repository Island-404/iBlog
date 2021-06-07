package com.island.myblog.mapper;

import com.island.myblog.common.dto.CategoryDTO;
import com.island.myblog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Component
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询分类和对应文章数量
     * @return 分类集合
     */
    List<CategoryDTO> listCategoryDTO();
}
