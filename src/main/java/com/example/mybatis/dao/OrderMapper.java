package com.example.mybatis.dao;

import com.example.mybatis.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> findOrderAndMemberByMemberId(@Param("memberId") Long memberId);

}
