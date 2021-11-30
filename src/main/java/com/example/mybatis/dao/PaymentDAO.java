package com.example.mybatis.dao;

import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDAO {

    @Insert("INSERT INTO payments (id, pay_method, amount, quota, card_num, card_name, paid_at, imp_uid) " +
            "VALUES (#{payment.merchantUid}, #{payment.payMethod}, #{payment.amount}, #{payment.quota}, #{payment.cardNum}, #{payment.cardName}, #{payment.paidAt}, #{payment.impUid})")
    int insert(@Param("payment") PaymentDto payment);

}
