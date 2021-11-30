package com.example.mybatis.service;

import com.example.mybatis.entity.Member;

public interface MailService {

    String sendAuthenticationMail(Member member);

    void sendFindPasswordMail(String email, String authKey);

    void sendPreConfiguredMail(String message);

}
