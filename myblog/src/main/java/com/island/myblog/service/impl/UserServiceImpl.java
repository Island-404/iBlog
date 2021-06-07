package com.island.myblog.service.impl;

import cn.hutool.crypto.SecureUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.island.myblog.common.dto.UserDTO;
import com.island.myblog.common.dto.UserLoginDTO;
import com.island.myblog.common.vo.*;
import com.island.myblog.entity.User;
import com.island.myblog.mapper.UserMapper;
import com.island.myblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.island.myblog.utils.QiniuCloudUtil;
import com.island.myblog.utils.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.island.myblog.common.constant.CommonConst.*;
import static com.island.myblog.common.constant.RedisPrefixConst.ARTICLE_USER_LIKE;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<UserDTO> getUserListByNickname(String nickname) {

        List<UserDTO> userDTOS = userMapper.getUserListByNickname(nickname);
        return userDTOS;
    }

    @Override
    public UserDTO getUserById(Integer id) {
        UserDTO userDTO = userMapper.getUserById(id);
        return userDTO;
    }

    @Override
    public int updateUserRoles(UserRolesVo userRolesVo) {
        int res = userMapper.deleteUserRolesByUid(userRolesVo.getUid());
        if(res<0) return res;
        return userMapper.setUserRoles(userRolesVo);
    }

    @Override
    public int updateUserStatusByUid(UserStatusVo userStatusVo) {
        User user = new User();
        user.setId(userStatusVo.getUid());
        user.setEnabled(userStatusVo.getEnabled());
        int res = userMapper.updateById(user);
        return res;
    }

    @Override
    public int deleteUserByUid(Integer id) {
        int res = userMapper.deleteUserRolesByUid(id);
        if(res<0) return res;
        return userMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(RegisterVo registerVo) {
        User user = User.builder()
                .username(registerVo.getUsername())
                .nickname(DEFAULT_NICKNAME)
                .password(SecureUtil.md5(registerVo.getPassword()))
                .enabled(DEFAULT_ENABLE)
                .email(registerVo.getEmail())
                .userface(DEFAULT_AVATAR)
                .build();
        userMapper.insert(user);
        userMapper.saveUserRoles(user.getId());
    }

    @Override
    public UserLoginDTO getUserInfo(User user) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(user, userLoginDTO);
        Set<Integer> articleLikeSet = (Set<Integer>) redisTemplate.boundHashOps(ARTICLE_USER_LIKE).get(user.getId().toString());
        userLoginDTO.setArticleLikeSet(articleLikeSet);
        return userLoginDTO;
    }

    @Override
    public int savaOrUpdateUserInfo(UserInfoVO userInfoVO) {
        // System.out.println(ShiroUtil.getProfile().getId()+"12");
        User user = User.builder()
                .id(ShiroUtil.getProfile().getId())
                .nickname(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .email(userInfoVO.getEmail())
                .build();
        int res = userMapper.updateById(user);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {
        // 查询旧密码是否正确
        User userInfo = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, ShiroUtil.getProfile().getId()));
        System.out.println(userInfo);
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(userInfo) && userInfo.getPassword().equals(SecureUtil.md5(passwordVO.getOldPassword()))) {
            User user = User.builder()
                    .id(userInfo.getId())
                    .password(SecureUtil.md5(passwordVO.getNewPassword()))
                    .build();
            userMapper.updateById(user);
        } else {
            throw new RuntimeException("旧密码不正确");
        }
    }

    @Override
    public String updateUserAvatar(MultipartFile file) {
        // 头像上传七牛云，返回图片地址
        String userface = QiniuCloudUtil.uploadImg(file);

        User user = User.builder()
                .id(ShiroUtil.getProfile().getId())
                .userface(userface)
                .build();
        userMapper.updateById(user);
        return userface;
    }


}
