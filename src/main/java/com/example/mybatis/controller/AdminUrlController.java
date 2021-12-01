package com.example.mybatis.controller;

import com.example.mybatis.entity.Url;
import com.example.mybatis.service.impl.UrlServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUrlController {

    private final UrlServiceImpl urlService;

    @GetMapping("/urls")
    public String getAllUrls(Model model) {
        model.addAttribute("urls", urlService.findAllUrls());
        return "admin/urlList";
    }

    @GetMapping("/urls/{input}")
    public ResponseEntity<List<Url>> getUrlsByInput(@PathVariable String input) {
        List<Url> urls = urlService.findUrlsByInput(input);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @GetMapping("/url/{id}")
    public String getUrl(@PathVariable Long id, Model model) {
        model.addAttribute("url", urlService.findUrlById(id));
        return "admin/url";
    }

    @GetMapping("/url/delete")
    public String deleteUrl(@RequestParam Long id, Model model) {
        log.info("id={}", id);
        int result = urlService.removeUrl(id);
        if (result == 0) {
            model.addAttribute("message", "Error");
            return "message";
        }
        return "redirect:/admin/urls";
    }

}
