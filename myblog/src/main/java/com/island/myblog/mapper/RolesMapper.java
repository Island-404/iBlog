package com.island.myblog.mapper;

import com.island.myblog.entity.Roles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Component
public interface RolesMapper extends BaseMapper<Roles> {

    /**
     * 根据用户id查找用户角色
     *
     * @param uid 用户id
     * @return 用户角色集合
     */
    Set<String> getRolesByUid(Integer uid);

}
