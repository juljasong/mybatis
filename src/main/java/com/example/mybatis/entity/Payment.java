package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private String merchantUid;
    private Long memberId;
    private Long productId;

    private String payMethod;
    private int applyNum;
    private String cardName;
    private String cardNum;
    private Integer quota;
    private Long amount;
    private int paidAt;

}
