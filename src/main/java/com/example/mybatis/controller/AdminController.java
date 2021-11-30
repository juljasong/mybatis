package com.example.mybatis.controller;

import com.example.mybatis.entity.Admin;
import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Order;
import com.example.mybatis.entity.Url;
import com.example.mybatis.service.MemberService;
import com.example.mybatis.service.impl.OrderServiceImpl;
import com.example.mybatis.service.impl.UrlServiceImpl;
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
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UrlServiceImpl urlService;
    private final MemberService memberService;
    private final OrderServiceImpl orderService;

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
    public String getAllUrls(Model model) {
        model.addAttribute("urls", urlService.findAll());
        return "admin/urls";
    }

    @GetMapping("/urls/{input}")
    public ResponseEntity<List<Url>> getUrlsByInput(@PathVariable String input) {
        List<Url> urls = urlService.findByInput(input);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @GetMapping("/members")
    public String getAllMembers(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "admin/members";
    }

    @GetMapping("/members/{input}")
    public ResponseEntity<List<Member>> getMembersByInput(@PathVariable String input) {
        List<Member> members = memberService.findByInput(input);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    @GetMapping("/orders/{input}")
    public ResponseEntity<List<Order>> getOrdersByInput(@PathVariable String input) {
        List<Order> orders = orderService.findByInput(input);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    private void login(HttpServletRequest request, Admin loginAdmin) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_ADMIN, loginAdmin);
    }

}
