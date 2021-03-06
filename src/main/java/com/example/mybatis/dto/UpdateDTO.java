package com.example.mybatis.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class UpdateDTO {

    private String email;

    private String currentPassword;

    private String password;
    private String password2;

    @NotBlank
    @Length(min = 3, max = 30)
    private String name;

}
