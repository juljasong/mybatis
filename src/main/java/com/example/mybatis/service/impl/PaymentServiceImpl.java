package com.example.mybatis.service.impl;

import com.example.mybatis.dao.PaymentDAO;
import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public void add(PaymentDto payment) throws Exception {
        int result = paymentDAO.insert(payment);

        if(result == 0) {
            throw new Exception();
        }
    }

}
