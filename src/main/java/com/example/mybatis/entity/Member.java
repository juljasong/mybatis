package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter @Setter
public class Member {

    private Long id;
    private String email;
    private String password;
    @Length(min = 3, max = 30)
    private String name;
    private Date signInDate;
    private String authKey;
    private String provider;

    public Member() {
    }

    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String password, String name, String authKey, String provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.authKey = authKey;
        this.provider = provider;
    }
}

