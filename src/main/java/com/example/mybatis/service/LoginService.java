package com.example.mybatis.service;

import com.example.mybatis.dto.LoginDto;
import com.example.mybatis.entity.Member;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface LoginService {

    Member login(LoginDto loginDto);

    Member loginOauth2ByGoogle(String credential, String g_csrf_token) throws GeneralSecurityException, IOException;

}
