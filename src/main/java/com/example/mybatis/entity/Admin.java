package com.example.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Admin {

    private Long id;
    private String adminId;
    private String password;
    private String name;

}
