package com.example.mybatis.controller;

import com.example.mybatis.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping("/complete")
    public void completePayments(Model model, @ModelAttribute Payment payment) {
        log.info("merchantUid={}, payMethod={}, amount={}", payment.getMerchantUid(), payment.getPayMethod(), payment.getAmount());

    }
}
