package com.example.mybatis.service;

import com.example.mybatis.dao.OrderMapper;
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

    public List<Order> showOrder(Long memberId) {
        return orderMapper.findOrderAndMemberByMemberId(memberId);
    }

}
