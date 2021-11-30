package com.example.mybatis.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PaymentDTO {

    @NotNull
    private String merchantUid;
    @NotNull
    private String impUid;
    @NotNull
    private String buyerEmail;
    @NotNull
    private String buyerName;
    @NotNull
    private String payMethod;
    @NotNull
    private int applyNum;
    @NotNull
    private String cardName;
    @NotNull
    private String cardNum;
    private Integer quota;
    @NotNull
    private int amount;
    @NotNull
    private int paidAt;
    private Long productId;
    private Long memberId;

}
