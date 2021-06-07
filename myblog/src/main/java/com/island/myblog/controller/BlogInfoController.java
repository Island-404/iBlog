package com.island.myblog.controller;


import com.island.myblog.common.dto.BlogHomeInfoDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.service.BlogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * @author xiaojie
 * @since 2020-05-18
 */
@Api(tags = "博客信息模块")
@RestController
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    @ApiOperation(value = "查看博客信息")
    @GetMapping("/info")
    public Result getBlogHomeInfo() {
        BlogHomeInfoDTO blogHomeInfoDTO = blogInfoService.getBlogInfo();
        System.out.println("11111");
        return Result.succ(200,"查询成功",blogHomeInfoDTO);
    }

    @ApiOperation(value = "查看后台信息")
    @GetMapping("/dataCharts")
    public Result getBlogBackInfo() {
        return Result.succ(200,"查询成功",blogInfoService.getBlogBackInfo());
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result getAbout() {
        String about = blogInfoService.getAbout();
        return Result.succ("查找成功",about);
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public Result updateAbout(String mdContent) {

        blogInfoService.updateAbout(mdContent);
        return Result.succ("修改成功",null);
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "修改公告")
    @PutMapping("/admin/notice")
    public Result updateNotice(String notice) {
        blogInfoService.updateNotice(notice);
        return Result.succ("修改成功",null);
    }

    @ApiOperation(value = "查看公告")
    @GetMapping("/admin/notice")
    public Result getNotice() {
        return Result.succ("查询成功",blogInfoService.getNotice());
    }

}

