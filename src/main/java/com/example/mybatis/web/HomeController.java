package com.example.mybatis.web;

import com.example.mybatis.web.login.LoginDto;
import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberMapper memberMapper;
    private final UrlMapper urlMapper;

    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member loginUser,  Model model) {

        if (loginUser == null) {
            model.addAttribute("loginDto", new LoginDto());
            return "home";
        }
            List<Url> urls = urlMapper.findByMemberId(loginUser.getId());
            List<Url> disabledUrls = urlMapper.findExpiredByMemberId(loginUser.getId());

            model.addAttribute("url", new Url());
            model.addAttribute("urls", urls);
            model.addAttribute("disabledUrls", disabledUrls);
            model.addAttribute("loginUser", loginUser);
            return "loginHome";
    }
}
