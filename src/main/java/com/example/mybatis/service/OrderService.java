package com.example.mybatis.service;

import com.example.mybatis.dao.OrderMapper;
import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    public void add(PaymentDto paymentDto) {
        int result = orderMapper.insert(paymentDto);
    }

    public Order findAvailableOrderByMemberId(Long id) {
        return orderMapper.findAvailableOrderByMemberId(id);
    }

    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    public List<Order> findByInput(String input) {
        return orderMapper.findByInput(input);
    }
}
