package com.example.mybatis.web;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberMapper memberMapper;

    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member loginUser,  Model model){

        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("loginUser", loginUser);
        return "loginHome";
    }
}
