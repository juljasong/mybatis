package com.example.mybatis.controller;

import com.example.mybatis.domain.Member;
import com.example.mybatis.domain.MemberDTO;
import com.example.mybatis.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.websocket.Session;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private MemberMapper memberMapper;

    @Autowired
    public MemberController(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @GetMapping("/add")
    public String addForm() {
        return "member/addForm";
    }

    @PostMapping("/add")
    public String addForm(Member member) {
        memberMapper.insert(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(MemberDTO member) throws Exception {
        Member loginUser = memberMapper.login(member);
        log.info("email={}, password={}", member.getEmail(), member.getPassword());
        log.info(loginUser.getName());
        return "redirect:/";
    }
}
