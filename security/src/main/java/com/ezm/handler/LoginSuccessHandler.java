package com.ezm.handler;

import com.alibaba.fastjson.JSON;
import com.ezm.common.response.ResultEnum;
import com.ezm.common.security.SecurityResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功拦截器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.loginSuccessful)));
    }

}
