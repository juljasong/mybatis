package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Setter
@Getter
@ToString
public class Order {

    private Long id;
    private Member member;
    private Product product;

    private Date startDate;
    private Date endDate;

}
