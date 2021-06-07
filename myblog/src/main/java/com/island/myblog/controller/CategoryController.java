package com.island.myblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.island.myblog.common.dto.ArticlePreviewDTO;
import com.island.myblog.common.dto.ArticlePreviewListDTO;
import com.island.myblog.common.dto.CategoryDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.vo.ConditionVO;
import com.island.myblog.entity.Category;
import com.island.myblog.service.ArticleService;
import com.island.myblog.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "分类列表")
    @GetMapping("/admin/category/all")
    public Result getCategoryList(){
        List<Category> categories = categoryService.list(null);
        return Result.succ(categories);
    }

    @ApiOperation(value = "分类DTO列表")
    @GetMapping("/categories")
    public Result getCategoryDTOList(){
        List<CategoryDTO> categoryDTO = categoryService.listCategoryDTO();
        return Result.succ(categoryDTO);
    }


    @RequiresRoles("超级管理员")
    @ApiOperation(value = "更新分类")
    @PutMapping("/admin/category")
    public Result updateCategory(Category category){
        boolean res = categoryService.updateById(category);
        if (!res){
            return Result.fail("更新失败！","error");
        }
        return Result.succ("更新成功","success");
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "添加分类")
    @PostMapping("/admin/category")
    public Result addCategory(Category category){

        if("".equals(category.getCateName()) && category.getCateName()==null){
            return Result.fail("请输入栏目名称","error");
        }
        boolean res = categoryService.save(category);
        if (!res){
            return Result.fail("添加失败","error");
        }
        return Result.succ("添加成功","success");
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "删除文章")
    @DeleteMapping("/admin/category/{ids}")
    public Result deleteCategory(@PathVariable("ids") List<Integer> ids){

        int res = categoryService.deleteCategory(ids);

        if (res==0){
            return Result.fail("删除失败","error");
        }
        return Result.succ("删除成功","success");
    }

    @ApiOperation(value = "查看分类下对应的文章")
    @GetMapping("/categories/{categoryId}")
    public Result listArticlesByCategoryId(@PathVariable("categoryId") Integer categoryId, Integer current) {
        ConditionVO conditionVO = ConditionVO.builder()
                .categoryId(categoryId)
                .current(current)
                .build();
        ArticlePreviewListDTO articlePreviewListDTO =  articleService.listArticlesByCondition(conditionVO);
        return Result.succ("查询成功",articlePreviewListDTO);
    }
}

