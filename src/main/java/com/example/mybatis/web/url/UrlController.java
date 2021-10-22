package com.example.mybatis.web.url;

import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/url/add")
    public String add(@ModelAttribute Url url,
                      @RequestParam String date) throws Exception {

        stringToDate(url, date);

        int result = urlService.add(url);
        if (result == 0) {
            throw new Exception("Error");
        }
        return "redirect:/";
    }


    @GetMapping("/{userName}")
    public String individual(@PathVariable String userName, Model model) {
        List<Url> urls = urlService.listByMemberName(userName);
        model.addAttribute("urls", urls);
        return "individual";
    }

    @GetMapping("/url/delete/{id}")
    public String deleteUrl(@PathVariable Long id) throws Exception {
        int result = urlService.deleteUrl(id);
        if(result < 0) {
            throw new Exception("Not exist url id");
        }
        return "redirect:/";
    }

    @PostMapping("/url/update")
    public String updateUrl(@RequestParam Long memberId,
                            @ModelAttribute Url url,
                            @RequestParam String date) throws Exception {
        stringToDate(url, date);
        int result = urlService.updateUrl(memberId, url);
        if(result < 0) {
            throw new Exception("Not exist url id");
        }
        return "redirect:/";
    }

    private void stringToDate(Url url, String date) {
        if (date.length() > 0) {
            url.setExpirationDate(Date.valueOf(date));
        }
    }
}
