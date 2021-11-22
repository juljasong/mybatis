package com.example.mybatis.controller;

import com.example.mybatis.entity.Order;
import com.example.mybatis.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/test")
    public String test(Model model) {

        Long memberId=11L;

        List<Order> orders = orderService.showOrder(memberId);
        for (Order order : orders) {
            log.info("{}", order);
        }
        model.addAttribute("message", "ok");
        return "message";
    }


}
