package com.ezm.handler;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话失效后的拦截
 */
@Component
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {


    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //cookie是双key(path,name),value的方式
        Cookie cookie = new Cookie("requestUrl",request.getRequestURI());
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/logout");
    }


}
