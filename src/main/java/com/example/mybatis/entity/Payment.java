package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Payment {

    private String merchantUid;
    private Member member;
    private Product product;

    private String payMethod;
    private int applyNum;
    private String cardName;
    private String cardNum;
    private Integer quota;
    private Long amount;
    private int paidAt;

}
