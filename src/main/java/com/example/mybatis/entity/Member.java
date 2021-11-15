package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter @Setter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Date signInDate;
    private String authKey;
    private String provider;

    public Member() {}

}
