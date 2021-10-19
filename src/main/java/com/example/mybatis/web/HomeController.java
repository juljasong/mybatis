package com.example.mybatis.web;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberMapper memberMapper;

    @RequestMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model){

        if (memberId == null) {
            return "home";
        }

        Member loginUser = memberMapper.findById(memberId);

        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("loginUser", loginUser);
        return "loginHome";
    }
}
