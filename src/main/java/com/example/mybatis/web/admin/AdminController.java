package com.example.mybatis.web.admin;

import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UrlMapper urlMapper;

    @GetMapping("/url")
    public String allUrl(Model model) {
        List<Url> urls = urlMapper.findAll();
        model.addAttribute("urls", urls);
        return "admin/url";
    }

}
