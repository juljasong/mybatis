package com.example.mybatis.service;

import com.example.mybatis.dao.PaymentMapper;
import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;

    public void add(PaymentDto payment) throws Exception {
        int result = paymentMapper.insert(payment);

        if(result == 0) {
            throw new Exception();
        }
    }

}
