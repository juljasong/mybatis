package com.example.mybatis.web.login;

import com.example.mybatis.domain.login.LoginService;
import com.example.mybatis.domain.member.Member;
import com.example.mybatis.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    //@PostMapping("/login")
    public String loginByCookie(@RequestParam String email, @RequestParam String password, HttpServletResponse response) throws Exception {

        Member loginUser = loginService.login(email, password);

        if (loginUser == null) {
            throw new Exception("Incorrect Email or Password.");
        } else {
            log.info("Login email = {}", email);

            Cookie idCookie = new Cookie("memberId", String.valueOf(loginUser.getId()));
            response.addCookie(idCookie);

            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String loginByHttpSession(@RequestParam String email, @RequestParam String password, HttpServletRequest request) throws Exception {

        Member loginUser = loginService.login(email, password);

        if (loginUser == null) {
            throw new Exception("Incorrect Email or Password.");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, loginUser);

            log.info("Login email = {}", email);
            log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());

            return "redirect:/";
        }
    }

    //@RequestMapping("/logout")
    public String logoutByCookie(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    /**
     * @param response
     * @param cookieName: 만료할 Cookie명
     */
    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @RequestMapping("/logout")
    public String logoutByHttpSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
