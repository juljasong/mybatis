package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private String merchantUid;
    //private Long memberId;

    private String payMethod;
    private String orderName;
    private Long applyNum;
    private String cardName;
    private String cardNum;
    private Integer cardQuota;
    private Long paidAmount;


}
