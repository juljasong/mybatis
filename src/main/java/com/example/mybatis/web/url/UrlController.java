package com.example.mybatis.web.url;

import com.example.mybatis.domain.url.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/url")
@Slf4j
public class UrlController {

    @PostMapping("/add")
    public String add(@ModelAttribute Url url) {
        log.info("name={}, url={}, description={}, expirationDate={}, isPublic={}", url.getName(), url.getUrl(), url.getDescription(), url.getExpirationDate(), url.getIsPublic());
        return "redirect:/";
    }
}
