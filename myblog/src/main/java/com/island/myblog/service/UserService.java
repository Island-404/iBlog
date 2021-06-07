package com.island.myblog.service;

import com.island.myblog.common.dto.UserDTO;
import com.island.myblog.common.dto.UserLoginDTO;
import com.island.myblog.common.vo.*;
import com.island.myblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
public interface UserService extends IService<User> {


    /**
     * 根据nickname查询
     *
     * @param nickname
     * @return
     */
    List<UserDTO> getUserListByNickname(String nickname);

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    UserDTO getUserById(Integer id);

    /**
     * update用户角色
     *
     * @param userRolesVo
     * @return
     */
    int updateUserRoles(UserRolesVo userRolesVo);

    /**
     * 根据用户id更新用户状态
     *
     * @param userStatusVo
     * @return
     */
    int updateUserStatusByUid(UserStatusVo userStatusVo);

    /**
     * 根据用户id删除用户信息
     *
     * @param id
     * @return
     */
    int deleteUserByUid(Integer id);


    /**
     * 保存注册信息
     *
     * @param registerVo
     */
    void saveUser(RegisterVo registerVo);

    /**
     *
     * @param user 用户信息
     * @return
     */
    UserLoginDTO getUserInfo(User user);

    /**
     *
     * @param userInfoVO 用户资料
     * @return
     */
    int savaOrUpdateUserInfo(UserInfoVO userInfoVO);

    /**
     *
     * @param passwordVO 密码信息
     */
    void updateAdminPassword(PasswordVO passwordVO);

    /**
     *
     * @param file
     * @return 图片url
     */
    String updateUserAvatar(MultipartFile file);
}
