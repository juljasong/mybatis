package com.example.mybatis.controller;

import com.example.mybatis.entity.Admin;
import com.example.mybatis.entity.Url;
import com.example.mybatis.service.UrlService;
import com.example.mybatis.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UrlService urlService;

    @PostMapping("/login")
    public String adminLogin(@ModelAttribute Admin admin, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if (admin.getAdminId().equals("admin") && admin.getPassword().equals("admin")) {
            Admin loginAdmin = new Admin();
            loginAdmin.setAdminId(admin.getAdminId());
            loginAdmin.setName("admin1");

            login(request, loginAdmin);

            return "redirect:/admin";
        }
        redirectAttributes.addFlashAttribute("message", "Incorrect.");
        return "redirect:/message";
    }

    @GetMapping("/logout")
    public String adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null){
            session.invalidate();
        }
        return "redirect:/admin";
    }

    @GetMapping("/url")
    public String allUrl(Model model) {
        List<Url> urls = urlService.findAll();
        model.addAttribute("urls", urls);
        return "admin/url";
    }

    private void login(HttpServletRequest request, Admin loginAdmin) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_ADMIN, loginAdmin);
    }

}
