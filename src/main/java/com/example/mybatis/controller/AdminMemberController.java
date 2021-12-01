package com.example.mybatis.controller;

import com.example.mybatis.entity.Member;
import com.example.mybatis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String getAllMembers(Model model) {
        model.addAttribute("members", memberService.findAllMembers());
        return "admin/members";
    }

    @GetMapping("/{input}")
    public ResponseEntity<List<Member>> getMembersByInput(@PathVariable String input) {
        List<Member> members = memberService.findMembersByInput(input);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }


}
