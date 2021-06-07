package com.island.myblog.utils;

import com.island.myblog.common.dto.UserLoginDTO;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static UserLoginDTO getProfile() {
        return (UserLoginDTO) SecurityUtils.getSubject().getPrincipal();
    }
}
