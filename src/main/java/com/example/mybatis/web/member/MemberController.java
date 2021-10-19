package com.example.mybatis.web.member;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String save(@ModelAttribute Member member) {
        memberMapper.insert(member);
        return "redirect:/";
    }

}
