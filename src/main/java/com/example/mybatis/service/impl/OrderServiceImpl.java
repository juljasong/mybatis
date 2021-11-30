package com.example.mybatis.service.impl;

import com.example.mybatis.dao.OrderDAO;
import com.example.mybatis.dto.PaymentDTO;
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

    public void addOrder(PaymentDTO paymentDTO) {
        int result = orderDAO.insert(paymentDTO);
    }

    public Order findAvailableOrderByMemberId(Long id) {
        return orderDAO.selectAvailableOrderByMemberId(id);
    }

    public List<Order> findAllOrders() {
        return orderDAO.selectAllOrders();
    }

    public List<Order> findOrderByInput(String input) {
        return orderDAO.selectOrderByInput(input);
    }
}
