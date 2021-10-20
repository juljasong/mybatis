package com.example.mybatis.web;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlMapper;
import com.example.mybatis.domain.url.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberMapper memberMapper;
    private final UrlMapper urlMapper;

    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member loginUser,  Model model) {

        if (loginUser == null) {
            return "home";
        }

        List<Url> urls = urlMapper.findByMemberId(loginUser.getId());
        List<Url> disabledUrls = urlMapper.findExpiredByMemberId(loginUser.getId());

        model.addAttribute("urls", urls);
        model.addAttribute("disabledUrls", disabledUrls);
        model.addAttribute("loginUser", loginUser);
        return "loginHome";
    }
}
