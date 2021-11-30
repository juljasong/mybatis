package com.example.mybatis.controller;

import com.example.mybatis.entity.Member;
import com.example.mybatis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/oauth2")
public class OauthController {

    private final MemberService memberService;

    @PostMapping("/add")
    public String addOauth2(@RequestParam String email, @RequestParam String name, @RequestParam String provider, RedirectAttributes redirectAttributes) {
        String message = memberService.saveOauth2(new Member(email, "google", name, "Y", provider));
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/message";
    }

    @PostMapping("/link")
    public String linkOauth2(@RequestParam String email, @RequestParam String provider, RedirectAttributes redirectAttributes) {
        String message = memberService.linkOauth2(email, provider);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/message";
    }

}
