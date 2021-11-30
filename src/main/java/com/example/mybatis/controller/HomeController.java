package com.example.mybatis.controller;

import com.example.mybatis.service.UrlService;
import com.example.mybatis.util.argumentResolver.Login;
import com.example.mybatis.dto.LoginDto;
import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UrlService urlService;

    @RequestMapping("/")
    public String home(//@SessionAttribute(name = SessionConst.LOGIN_USER, required = false)
                       @Login Member loginUser, Model model) {

        if (loginUser == null) {
            model.addAttribute("loginDto", new LoginDto());
            return "home";
        }
            List<Url> enableUrls = urlService.findByMemberId(loginUser.getId());
            List<Url> disabledUrls = urlService.findExpiredByMemberId(loginUser.getId());

            model.addAttribute("url", new Url());
            model.addAttribute("enableUrls", enableUrls);
            model.addAttribute("disabledUrls", disabledUrls);
            model.addAttribute("loginUser", loginUser);

            return "loginHome";
    }

}
