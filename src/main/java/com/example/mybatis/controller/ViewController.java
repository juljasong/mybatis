package com.example.mybatis.controller;

import com.example.mybatis.entity.Url;
import com.example.mybatis.service.impl.UrlServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

    private final UrlServiceImpl urlService;

    @GetMapping("/{userName}")
    public String individual(@PathVariable String userName, Model model) {
        List<Url> urls = urlService.findByMemberIdAndPublic(userName);
        model.addAttribute("urls", urls);
        return "individual";
    }

}
