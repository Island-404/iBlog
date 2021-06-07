package com.island.myblog.service.impl;

import com.island.myblog.entity.Roles;
import com.island.myblog.mapper.RolesMapper;
import com.island.myblog.service.RolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements RolesService {

    @Autowired
    @Qualifier("rolesMapper")
    private RolesMapper rolesMapper;

    @Override
    public Set<String> getRolesByUid(Integer uid) {
        return rolesMapper.getRolesByUid(uid);
    }
}
