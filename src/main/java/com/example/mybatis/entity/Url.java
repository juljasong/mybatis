package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter @Setter
@ToString
public class Url {

    private Long id;
    private Long memberId;

    @NotBlank
    private String name;
    @NotBlank
    private String url;

    private String description;
    private Date expirationDate;
    private int isPublic;

}
