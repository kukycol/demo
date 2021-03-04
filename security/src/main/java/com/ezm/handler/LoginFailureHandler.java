package com.ezm.handler;

import com.alibaba.fastjson.JSON;
import com.ezm.common.response.ResultEnum;
import com.ezm.common.security.SecurityResult;
import com.ezm.dao.mapper.security.SecurityUserMapper;
import com.ezm.entity.table.SecurityUser;
import com.ezm.utils.CheckUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 错误拦截
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        response.setContentType("application/json;charset=utf-8");

        //账号或密码错误
        if (e instanceof BadCredentialsException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.wrongPassword)));

        //账号锁定
        } else if (e instanceof LockedException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.accountNonLocked)));

        //密码过期
        } else if (e instanceof CredentialsExpiredException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.credentialsNonExpired)));

        //账号过期
        } else if (e instanceof AccountExpiredException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.accountNonExpired)));

        //账号未启用
        } else if (e instanceof DisabledException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.enabled)));

        }

    }


}
