package com.island.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.island.myblog.common.dto.CategoryDTO;
import com.island.myblog.entity.Article;
import com.island.myblog.entity.Category;
import com.island.myblog.mapper.ArticleMapper;
import com.island.myblog.mapper.CategoryMapper;
import com.island.myblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<CategoryDTO> listCategoryDTO() {
        return categoryMapper.listCategoryDTO();
    }

    @Override
    public int deleteCategory(List<Integer> ids) {
        // 查询分类id下是否有文章
        Integer count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCid, ids));
        if (count > 0) {
            throw new RuntimeException("删除失败，该分类下存在文章");
        }
        int res = categoryMapper.deleteBatchIds(ids);
        return res;
    }
}
