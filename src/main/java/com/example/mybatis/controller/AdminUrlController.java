package com.example.mybatis.controller;

import com.example.mybatis.entity.Url;
import com.example.mybatis.service.impl.UrlServiceImpl;
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
public class AdminUrlController {

    private final UrlServiceImpl urlService;

    @GetMapping("/urls")
    public String getAllUrls(Model model) {
        model.addAttribute("urls", urlService.findAllUrls());
        return "admin/urls";
    }

    @GetMapping("/urls/{input}")
    public ResponseEntity<List<Url>> getUrlsByInput(@PathVariable String input) {
        List<Url> urls = urlService.findUrlsByInput(input);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

}
