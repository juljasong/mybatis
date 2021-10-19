package com.example.mybatis.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MemberDTO {

    private String email;
    private String password;

    public MemberDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
