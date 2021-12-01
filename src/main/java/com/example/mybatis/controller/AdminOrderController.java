package com.example.mybatis.controller;

import com.example.mybatis.entity.Order;
import com.example.mybatis.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderServiceImpl orderService;

    @GetMapping("/")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAllOrders());
        return "admin/orders";
    }

    @GetMapping("/{input}")
    public ResponseEntity<List<Order>> getOrdersByInput(@PathVariable String input) {
        List<Order> orders = orderService.findOrderByInput(input);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
