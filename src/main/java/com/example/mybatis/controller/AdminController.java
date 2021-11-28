package com.example.mybatis.controller;

import com.example.mybatis.entity.Admin;
import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Order;
import com.example.mybatis.entity.Url;
import com.example.mybatis.service.MemberService;
import com.example.mybatis.service.OrderService;
import com.example.mybatis.service.UrlService;
import com.example.mybatis.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UrlService urlService;
    private final MemberService memberService;
    private final OrderService orderService;

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

    @GetMapping("/urls")
    public ResponseEntity<List<Url>> getAllUrls() {
        List<Url> urls = urlService.findAll();
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @GetMapping("urls/{input}")
    public ResponseEntity<List<Url>> getUrlsByInput(@PathVariable String input) {
        List<Url> urls = urlService.findByInput(input);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    private void login(HttpServletRequest request, Admin loginAdmin) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_ADMIN, loginAdmin);
    }

}
