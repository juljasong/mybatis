package com.example.mybatis.service;

import com.example.mybatis.dto.PaymentDTO;
import com.example.mybatis.entity.Order;

import java.util.List;

public interface OrderService {

    void addOrder(PaymentDTO paymentDTO);

    Order findAvailableOrderByMemberId(Long id);

    List<Order> findAllOrders();

    List<Order> findOrderByInput(String input);

}
