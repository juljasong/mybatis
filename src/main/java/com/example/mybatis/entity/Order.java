package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class Order {

    private Long id;
    private Long memberId;
    private Long productId;

    private Date startDate;
    private Date endDate;

}
