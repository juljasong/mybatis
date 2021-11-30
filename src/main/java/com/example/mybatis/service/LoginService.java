package com.example.mybatis.service;

import com.example.mybatis.dto.LoginDTO;
import com.example.mybatis.entity.Member;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface LoginService {

    Member login(LoginDTO loginDTO);

    Member loginOauth2ByGoogle(String credential, String g_csrf_token) throws GeneralSecurityException, IOException;

}
