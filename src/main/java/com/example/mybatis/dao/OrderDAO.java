package com.example.mybatis.dao;

import com.example.mybatis.dto.PaymentDTO;
import com.example.mybatis.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface OrderDAO {

    @Insert("INSERT INTO orders (id, member_id, product_id, start_date, end_date) " +
            "VALUES (#{paymentDTO.merchantUid}, #{paymentDTO.memberId}, #{paymentDTO.productId}, FROM_UNIXTIME(#{paymentDTO.paidAt}, '%Y-%m-%d %h:%i:%s'), FROM_UNIXTIME(#{paymentDTO.paidAt}+2592000, '%Y-%m-%d %h:%i:%s'))")
    int insert(@Param("paymentDTO") PaymentDTO paymentDTO);

    @Select("SELECT * FROM orders WHERE member_id=#{id} AND start_date < NOW() AND end_date > NOW()")
    Order selectAvailableOrderByMemberId(@Param("id") Long id);

    @Select("SELECT * FROM orders")
    List<Order> selectAllOrders();

    @Select("SELECT * FROM orders WHERE id LIKE CONCAT('%', #{input}, '%') OR member_id LIKE CONCAT('%', #{input}, '%') OR product_id LIKE CONCAT('%', #{input}, '%') OR start_date LIKE CONCAT('%', #{input}, '%') OR end_date LIKE CONCAT('%', #{input}, '%')")
    List<Order> selectOrderByInput(@Param("input") String input);
}
