package com.ara.todayoutfit.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    public static final String ADMIN_LOGIN_PATH = "/admin/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("id") == null) {
            response.sendRedirect(ADMIN_LOGIN_PATH);
            return false;
        }
        return true;
    }
}
