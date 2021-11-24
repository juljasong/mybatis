package com.example.mybatis.dao;

import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> findOrderAndMemberByMemberId(@Param("memberId") Long memberId);

    @Insert("INSERT INTO orders (id, member_id, product_id, start_date, end_date) " +
            "VALUES (#{paymentDto.merchantUid}, #{paymentDto.memberId}, #{paymentDto.productId}, FROM_UNIXTIME(#{paymentDto.paidAt}), FROM_UNIXTIME(#{paymentDto.paidAt}+7776000))")
    int insert(@Param("paymentDto")PaymentDto paymentDto);
}
