package com.example.mybatis.util.interceptor;

import com.example.mybatis.util.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.LOGIN_ADMIN) == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
