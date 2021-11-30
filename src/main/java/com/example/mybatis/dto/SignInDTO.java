package com.example.mybatis.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SignInDTO {

    @NotBlank @Email
    private String email;

    @Length(min = 10, max = 20)
    private String password;

    @Length(min = 3, max = 30)
    private String name;

}
