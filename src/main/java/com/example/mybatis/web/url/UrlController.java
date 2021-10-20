package com.example.mybatis.web.url;

import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/url")
@Slf4j
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    @PostMapping("/add")
    public String add(@ModelAttribute Url url) throws Exception {

        log.info("name={}, url={}, description={}, expirationDate={}, isPublic={}", url.getName(), url.getUrl(), url.getDescription(), url.getExpirationDate(), url.getIsPublic());
        
        int result = urlService.add(url);
        if (result == 0) {
            throw new Exception("Error");
        }
        return "redirect:/";
    }

}
