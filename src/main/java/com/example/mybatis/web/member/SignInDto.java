package com.example.mybatis.web.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class SignInDto {

    @NotBlank @Email
    private String email;

    @Length(min = 10, max = 20)
    private String password;

    @Length(min = 3, max = 30)
    private String name;

}
