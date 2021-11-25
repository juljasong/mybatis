package com.example.mybatis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MessageController {

    @GetMapping("/message")
    public String message() {
        return "message";
    }
}
