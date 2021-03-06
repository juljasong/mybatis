package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Payment {

    private String merchantUid;
    private String impUid;
    private String payMethod;
    private int applyNum;
    private String cardName;
    private String cardNum;
    private Integer quota;
    private int amount;
    private int paidAt;

}
