package com.island.myblog.service;

import com.island.myblog.common.dto.CategoryDTO;
import com.island.myblog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类和对应文章数量
     * @return 分类集合
     */
    List<CategoryDTO> listCategoryDTO();

    /**
     * 删除分类
     * @param ids 分类id集合
     * @return
     */
    int deleteCategory(List<Integer> ids);

}
