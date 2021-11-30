package com.example.mybatis.service;

import com.example.mybatis.dto.PaymentDTO;

public interface PaymentService {

    void addPayment(PaymentDTO payment) throws Exception;

}
