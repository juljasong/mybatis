package com.example.mybatis.service;

import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Order;

import java.util.List;

public interface OrderService {

    void add(PaymentDto paymentDto);

    Order findAvailableOrderByMemberId(Long id);

    List<Order> findAll();

    List<Order> findByInput(String input);

    
}
