package com.island.myblog.mapper;

import com.island.myblog.common.dto.UserDTO;
import com.island.myblog.common.vo.UserRolesVo;
import com.island.myblog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据nickname查询
     * @param nickname
     * @return
     */
    List<UserDTO> getUserListByNickname(String nickname);



    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    UserDTO getUserById(Integer id);



    /**
     * 更新用户角色信息
     * @param userRolesVo
     * @return
     */
    int setUserRoles(UserRolesVo userRolesVo);



    /**
     * 根据删除用户角色信息
     * @param uid
     * @return
     */
    int deleteUserRolesByUid(Integer uid);

    /**
     * 保存注册用户角色
     * @param uid 用户id
     * @return
     */
    int saveUserRoles(@Param("id") Integer uid);

}
