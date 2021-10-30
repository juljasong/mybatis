package com.example.mybatis.web.login;

import com.example.mybatis.domain.login.LoginDTO;
import com.example.mybatis.domain.login.LoginService;
import com.example.mybatis.domain.member.Member;
import com.example.mybatis.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final LoginService loginService;

    /*
    @PostMapping("/login")
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
    */

    @PostMapping("/auth/login")
    public String loginByHttpSession(@ModelAttribute LoginDTO loginDTO, BindingResult bindingResult, HttpServletRequest request) {

        Member loginUser = loginService.login(loginDTO);

        if (loginUser == null) {
            bindingResult.addError(new ObjectError("loginDTO", "Incorrect email or password."));
            log.info("error={}", bindingResult);

            return "home";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, loginUser);
            log.info("Login email={}", loginDTO.getEmail());
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
