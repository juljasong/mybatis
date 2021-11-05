package com.example.mybatis.web.filter;

import com.example.mybatis.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/member/add", "/logout", "/auth/login", "/css/*", "/js/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("Start Auth Check Filter: {}", requestURI);

            if (isLoginCheckPath(requestURI)) {

                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);

                if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null){

                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpResponse.sendRedirect("/?redirectURL=" + requestURI);
                    return;

                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
             log.info("End Auth Check Filter: {}", requestURI);
        }
    }

    /**
     * 화이트 리스트 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

}
