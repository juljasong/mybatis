package com.example.mybatis.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@ToString
public class PaymentFormDto {

    @NotNull
    private String merchantUid;
    @NotNull
    private String buyerEmail;
    @NotNull
    private String orderName;
    @NotNull
    private String buyerName;
    @NotNull
    private int amount;

}
