package com.island.myblog.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.island.myblog.common.dto.UserLoginDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.vo.LoginVo;
import com.island.myblog.common.vo.RegisterVo;
import com.island.myblog.entity.User;
import com.island.myblog.service.UserService;
import com.island.myblog.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController

public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    //@RequiresRoles(value = {"超级管理员","测试角色1"})
    @ApiOperation(value = "后台登录")
    @PostMapping("/admin/login")
    public Result adminLogin(@Validated LoginVo loginVo, HttpServletResponse response){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginVo.getUsername());
        User user = userService.getOne(wrapper);
        Assert.notNull(user,"用户不存在");
        System.out.println(SecureUtil.md5(loginVo.getPassword()));
        if (!user.getPassword().equals(SecureUtil.md5(loginVo.getPassword()))){

            return Result.fail("密码不正确",null);
        }

        Assert.isTrue(user.getEnabled(),"账号已冻结！");

        String jwt = jwtUtil.createJWT(user.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        return Result.succ(user);
    }

    @ApiOperation(value = "前台登录")
    @PostMapping("/login")
    public Result login(@Validated LoginVo loginVo, HttpServletResponse response){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginVo.getUsername());
        User user = userService.getOne(wrapper);
        Assert.notNull(user,"用户不存在");

        if (!user.getPassword().equals(SecureUtil.md5(loginVo.getPassword()))){

            return Result.fail("密码不正确",null);
        }
        Assert.isTrue(user.getEnabled(),"账号已冻结！");
        UserLoginDTO userLoginDTO = userService.getUserInfo(user);
        String jwt = jwtUtil.createJWT(user.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        return Result.succ("登录成功",userLoginDTO);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody RegisterVo registerVo){
        System.out.println(registerVo);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", registerVo.getUsername());
        User user = userService.getOne(wrapper);
        Assert.isNull(user,"用户已存在");
        userService.saveUser(registerVo);
        return Result.succ("注册成功");

    }

    @RequiresAuthentication
    @ApiOperation(value = "注销")
    @GetMapping("/logout")
    public Result logout(){
        System.out.println("退出成功");
        SecurityUtils.getSubject().logout();
        return Result.succ("退出成功",null);
    }



}
