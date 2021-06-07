package com.island.myblog.shiro;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.island.myblog.common.lang.Result;
import com.island.myblog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    private JwtUtil jwtUtil;


    // 创建token
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwt)){
            return null;
        }
        return new JwtToken(jwt);
    }

    // 判断是否有token
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("Authorization");
        if (!StringUtils.isEmpty(jwt)){
            return false;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        //System.out.println(jwt);
        if (StringUtils.isEmpty(jwt) || jwt==null){
            return true;
        }else {
            // 校验jwt
            Claims claims = jwtUtil.parseJWT(jwt);
            if(claims == null || jwtUtil.isTokenExpired(claims.getExpiration())){
                throw new ExpiredCredentialsException("token已失效，请重新登录!");
            }


        }
        //执行登录
        return executeLogin(servletRequest,servletResponse);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {


        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        response.setCharacterEncoding("utf-8");
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result result = Result.fail(20003,throwable.getMessage(),null);

        String json = JSONUtil.toJsonStr(result);
        // System.out.println(json);
        try {

            httpServletResponse.getWriter().write(json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {


        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-control-Allow-Headers",httpServletRequest.getHeader("Access-Control-Request-Headers"));

        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
