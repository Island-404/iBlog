package com.island.myblog.shiro;

import com.island.myblog.common.dto.UserLoginDTO;
import com.island.myblog.entity.User;
import com.island.myblog.service.RolesService;
import com.island.myblog.service.UserService;
import com.island.myblog.utils.JwtUtil;
import com.island.myblog.utils.ShiroUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Integer userId = ShiroUtil.getProfile().getId();
        Set<String> roles = rolesService.getRolesByUid(userId);
        System.out.println(roles);
        info.setRoles(roles);
        return info;
    }

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String userId = jwtUtil.parseJWT((String) jwtToken.getPrincipal()).getSubject();
        System.out.println(userId);
        User user = userService.getById(Integer.valueOf(userId));
        if (user==null){
            throw new UnknownAccountException("账户不存在");
        }

        if(!user.getEnabled()){
            throw new LockedAccountException("账户已被锁定"); //帐号锁定
        }
        UserLoginDTO profile = new UserLoginDTO();
        BeanUtils.copyProperties(user, profile);

        System.out.println("-----------------");
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
