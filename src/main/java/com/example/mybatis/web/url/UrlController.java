package com.example.mybatis.web.url;

import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/url")
@Slf4j
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/add")
    public String add(@ModelAttribute Url url) throws Exception {

        log.info("name={}, url={}, description={}, expirationDate={}, isPublic={}", url.getName(), url.getUrl(), url.getDescription(), url.getExpirationDate(), url.getIsPublic());

        int result = urlService.add(url);
        if (result == 0) {
            throw new Exception("Error");
        }
        return "redirect:/";
    }

    @GetMapping("/{userName}")
    public String deleteUser(@PathVariable String userName, Model model) {
        System.out.println("userName = " + userName);
        List<Url> urls = urlService.listByMemberName(userName);
        model.addAttribute("urls", urls);
        return  "individual";
    }

}
