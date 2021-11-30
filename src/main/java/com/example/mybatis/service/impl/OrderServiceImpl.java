package com.example.mybatis.service.impl;

import com.example.mybatis.dao.OrderDAO;
import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Order;
import com.example.mybatis.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    public void add(PaymentDto paymentDto) {
        int result = orderDAO.insert(paymentDto);
    }

    public Order findAvailableOrderByMemberId(Long id) {
        return orderDAO.findAvailableOrderByMemberId(id);
    }

    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    public List<Order> findByInput(String input) {
        return orderDAO.findByInput(input);
    }
}
