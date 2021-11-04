package com.example.mybatis.web.url;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.url.Url;
import com.example.mybatis.domain.url.UrlMapper;
import com.example.mybatis.domain.url.UrlService;
import com.example.mybatis.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final UrlMapper urlMapper;

    @PostMapping("/url/add")
    public String add(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member loginUser,
                      @Valid @ModelAttribute Url url, BindingResult bindingResult,
                      @RequestParam String date, RedirectAttributes redirectAttributes) throws Exception {

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            redirectAttributes.addAttribute("addFailure", true);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.url", bindingResult);
//            redirectAttributes.addFlashAttribute("url", url);
            return "redirect:/";
        }

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
        log.info("{}, {}", url, date);
        stringToDate(url, date);
        int result = urlService.updateUrl(memberId, url);
        if(result < 0) {
            throw new Exception("Not exist url id");
        }
        return "redirect:/";
    }

    private void stringToDate(Url url, String expirationDate) {
        if (expirationDate.length() > 0) {
            url.setExpirationDate(Date.valueOf(expirationDate));
        }
    }
}
