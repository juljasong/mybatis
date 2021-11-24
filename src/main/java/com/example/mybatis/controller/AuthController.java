package com.example.mybatis.controller;

import com.example.mybatis.dto.LoginDto;
import com.example.mybatis.entity.Member;
import com.example.mybatis.service.LoginService;
import com.example.mybatis.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

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
    public String loginByHttpSession(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                                     HttpServletRequest request) {

        Member loginUser = loginService.login(loginDto);

        if (loginUser == null) {
            log.info("error={}", bindingResult.getAllErrors());
            if (!loginDto.getEmail().isBlank() && !loginDto.getPassword().isBlank()) {
                bindingResult.addError(new ObjectError("loginDto", "Incorrect email or password."));
            }

            return "home";
        } else {
            return login(request, loginUser);
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

    @PostMapping("/login/oauth2/google")
    public String loginOauth2ByGoogle(@RequestParam String credential, @RequestParam String g_csrf_token,
                                      HttpServletRequest request, Model model) throws GeneralSecurityException, IOException {

        log.info("credential={}, g_csrf_token={} ", credential, g_csrf_token);
        Member member = loginService.loginOauth2ByGoogle(credential, g_csrf_token);

        if (member.getProvider() != null && member.getProvider().equals("google")) {

            return login(request, member);

        } else {
            //구글 연동된 email 존재 X
            if (member.getProvider() == null && member.getAuthKey() != null) {
                model.addAttribute("message", "The same email address exists. Do you want to connect?");
                model.addAttribute("provider", "google");
                model.addAttribute("member", member);
                return "member/linkForm";
            } else {
                model.addAttribute("message", "Do you want to sign up?");
                model.addAttribute("provider", "google");
                model.addAttribute("member", member);
                log.info("member.email={}", member.getEmail());
                return "member/oauthAddForm";
            }
        }
    }

    private String login(HttpServletRequest request, Member member) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, member);

        String ref = request.getHeader("Referer");

        if (ref.length() < 30) {
            log.info("Login email={}, loginTime={}", member.getEmail(), LocalDateTime.now());
            return "redirect:/";
        } else {
            String redirectUrl = ref.substring(35);
            log.info("Login email={}, loginTime={}, Redirect={}", member.getEmail(), LocalDateTime.now(), redirectUrl);
            return "redirect:" + redirectUrl;
        }
    }

}
