package com.example.mybatis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordDto {

    private String password1;
    private String password2;
    private String authKey;

    public FindPasswordDto(String password1, String password2, String authKey) {
        this.password1 = password1;
        this.password2 = password2;
        this.authKey = authKey;
    }
}
