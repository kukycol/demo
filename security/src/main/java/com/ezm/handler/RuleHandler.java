package com.ezm.handler;

import com.alibaba.fastjson.JSON;
import com.ezm.common.response.ResultEnum;
import com.ezm.common.security.SecurityResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 权限不足拦截器
 */
@Component
public class RuleHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        System.out.println(httpServletRequest);
        e.printStackTrace();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.permissionDenied)));
    }

}
