package com.example.mybatis.controller;

import com.example.mybatis.entity.Admin;
import com.example.mybatis.util.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class AdminHomeController {

    @GetMapping("/admin")
    public String adminHome(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Admin admin) {

        if (admin == null) {
            return "admin/home";
        }

        return "admin/url";
    }
}
