package com.example.mybatis.controller;

import com.example.mybatis.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;



}
