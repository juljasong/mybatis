package com.example.mybatis.controller;

import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.dto.PaymentFormDto;
import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Order;
import com.example.mybatis.entity.Product;
import com.example.mybatis.service.OrderService;
import com.example.mybatis.service.PaymentService;
import com.example.mybatis.service.ProductService;
import com.example.mybatis.util.argumentResolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/add")
    public ResponseEntity<PaymentFormDto> addPayment(@Login Member loginUser, @RequestParam Long productId) {

        Order order = orderService.findAvailableOrderByMemberId(loginUser.getId());

        if (order != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //주문번호 생성(주문 시간 + 해쉬화된 email)
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int hashedEmail = loginUser.getEmail().hashCode();
        String merchantUid = date + hashedEmail;

        Product product = productService.getById(productId);

        PaymentFormDto paymentFormDto = new PaymentFormDto();
        paymentFormDto.setMerchantUid(merchantUid);
        paymentFormDto.setOrderName(product.getName());
        paymentFormDto.setAmount(product.getPrice());
        paymentFormDto.setBuyerEmail(loginUser.getEmail());
        paymentFormDto.setBuyerName(loginUser.getName());

        log.info("{}", paymentFormDto);

        return new ResponseEntity<PaymentFormDto>(paymentFormDto, HttpStatus.OK);
    }

    @PostMapping("/complete")
    public ResponseEntity<PaymentDto> completePayments(@Login Member loginUser, @ModelAttribute PaymentDto paymentDto) {

        try {
            paymentDto.setBuyerName(loginUser.getName());
            paymentDto.setBuyerEmail(loginUser.getEmail());
            paymentDto.setMemberId(loginUser.getId());

            paymentService.add(paymentDto);
            orderService.add(paymentDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<PaymentDto>(paymentDto, HttpStatus.OK);
    }

}
