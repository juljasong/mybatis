package com.example.mybatis.domain.url;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class Url {

    private Long id;
    private Long memberId;

    private String name;
    private String url;
    private String description;
    private Date expirationDate;
    private int isPublic;

}
