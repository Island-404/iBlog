package com.island.myblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.island.myblog.common.dto.UserDTO;
import com.island.myblog.common.lang.Result;
import com.island.myblog.common.vo.PasswordVO;
import com.island.myblog.common.vo.UserInfoVO;
import com.island.myblog.common.vo.UserRolesVo;
import com.island.myblog.common.vo.UserStatusVo;
import com.island.myblog.entity.Roles;
import com.island.myblog.entity.User;
import com.island.myblog.service.RolesService;
import com.island.myblog.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;

    @ApiOperation(value = "获取用户角色列表")
    @GetMapping("/user")
    public Result getUserList(String nickname){
        List<UserDTO> users = userService.getUserListByNickname(nickname);
        return Result.succ(200,"查询成功！",users);
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable("id") Integer id){
        UserDTO user = userService.getUserById(id);
        return Result.succ(200,"查询成功！",user);
    }


    @ApiOperation(value = "获取角色表")
    @GetMapping("/user/roles")
    public Result getRoles(){
        List<Roles> roles = rolesService.list(null);
        return Result.succ(roles);
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "更新角色信息")
    @PutMapping("/admin/user/role")
    public Result udateUserRole(UserRolesVo userRolesVo){
        int res = userService.updateUserRoles(userRolesVo);
        if (res != userRolesVo.getRids().length){
             return Result.fail("更新失败！","fail");
        }
        return Result.succ("更新成功！","success");
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "更新用户状态")
    @PutMapping("/admin/user/enabled")
    public Result updateUserStatus(UserStatusVo userStatusVo){
        int res = userService.updateUserStatusByUid(userStatusVo);
        if (res!=1){
            return Result.fail("更新失败！");
        }
        return Result.succ("更新成功！");
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/admin/user/{id}")
    public Result deleteUser(@PathVariable("id") Integer id){
        int res = userService.deleteUserByUid(id);
        if(res!=1){
            return Result.fail("删除失败!");
        }
        return Result.succ("删除成功！");
    }

    @ApiOperation(value = "是否为管理员")
    @GetMapping("/isAdmin")
    public Result isAdmin(){

        return Result.succ(true);
    }

    @RequiresAuthentication
    @ApiOperation(value = "更新用户信息")
    @PutMapping("/user/info")
    public Result updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO){
        System.out.println(userInfoVO);
        int res = userService.savaOrUpdateUserInfo(userInfoVO);
        if (res!=1){
            return Result.fail("修改失败");

        }
        return Result.succ("修改成功");
    }

    @RequiresRoles("超级管理员")
    @ApiOperation(value = "修改管理员密码")
    @PutMapping("/admin/user/password")
    public Result updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO) {
        System.out.println(passwordVO);
        userService.updateAdminPassword(passwordVO);
        return Result.succ("修改成功",null);
    }

    @RequiresAuthentication
    @ApiOperation(value = "修改用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/users/avatar")
    public Result updateUserInfo(MultipartFile file) {
        System.out.println(file);
        String userface = userService.updateUserAvatar(file);
        if("".equals(userface) && userface==null){

            return Result.fail("上传失败",null);
        }
        return Result.succ("上传成功", userface);
    }
}

