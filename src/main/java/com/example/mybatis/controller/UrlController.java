package com.example.mybatis.controller;

import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Order;
import com.example.mybatis.entity.Url;
import com.example.mybatis.dao.UrlMapper;
import com.example.mybatis.service.OrderService;
import com.example.mybatis.service.UrlService;
import com.example.mybatis.util.argumentResolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;
    private final OrderService orderService;

    @GetMapping("/add")
    @ResponseBody
    public String enableAddUrl(@Login Member loginUser, @ModelAttribute Url url) {

        Order membership = orderService.findAvailableOrderByMemberId(loginUser.getId());
        int urls = urlService.countAllByMemberId(loginUser.getId());

        if (membership == null && urls > 4) {
            return "false";
        }
        return "ok";

    }

    @PostMapping("/add")
    public String add(@Login Member loginUser,
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

    @GetMapping("/delete/{id}")
    public String deleteUrl(@PathVariable Long id) throws Exception {
        int result = urlService.deleteUrl(id);
        if(result < 0) {
            throw new Exception("Not exist url id");
        }
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/update")
    public ResponseEntity<Url> updateForm(@Login Member loginUser, @RequestParam Long id) {
        Url url = urlService.findUrlById(loginUser.getId(), id);
        log.info("{}", url);
        return new ResponseEntity<Url>(url, HttpStatus.OK);
    }

    @PostMapping("/update")
    public String updateUrl(@Login Member loginUser,
                            @Valid @ModelAttribute Url url, BindingResult bindingResult,
                            @RequestParam String date, RedirectAttributes redirectAttributes) throws Exception {
        log.info("{}, {}", url, date);

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            redirectAttributes.addAttribute("updateFailure", true);

            return "redirect:/";
        }

        stringToDate(url, date);
        int result = urlService.updateUrl(loginUser, url);
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
