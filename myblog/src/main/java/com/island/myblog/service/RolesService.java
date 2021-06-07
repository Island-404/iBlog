package com.island.myblog.service;

import com.island.myblog.entity.Roles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
public interface RolesService extends IService<Roles> {

    /**
     * 根据用户id查找用户角色
     *
     * @param uid 用户id
     * @return 用户角色集合
     */
    Set<String> getRolesByUid(Integer uid);
}
