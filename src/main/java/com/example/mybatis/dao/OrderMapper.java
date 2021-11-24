package com.example.mybatis.dao;

import com.example.mybatis.dto.PaymentDto;
import com.example.mybatis.entity.Order;
import org.apache.ibatis.annotations.*;


@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders (id, member_id, product_id, start_date, end_date) " +
            "VALUES (#{paymentDto.merchantUid}, #{paymentDto.memberId}, #{paymentDto.productId}, FROM_UNIXTIME(#{paymentDto.paidAt}, '%Y-%m-%d %h:%i:%s'), FROM_UNIXTIME(#{paymentDto.paidAt}+7776000, '%Y-%m-%d %h:%i:%s'))")
    int insert(@Param("paymentDto")PaymentDto paymentDto);

    @Select("SELECT * FROM orders WHERE member_id=#{id} AND start_date < NOW() AND end_date > NOW()")
    Order findAvailableOrderByMemberId(@Param("id") Long id);
}
