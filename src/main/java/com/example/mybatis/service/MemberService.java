package com.example.mybatis.service;

import com.example.mybatis.entity.Member;

import java.util.List;

public interface MemberService {

    Member findMemberById(Long id);

    int save(Member member) throws Exception;

    int verifyCurrentPassword(Long id, String currentPassword);

    String findPassword(String email);

    String resetPassword(String password1, String authKey);

    String saveOauth2(Member member);

    String linkOauth2(String email, String provider);

    String mailCheck(String authKey);

    List<Member> findAll();

    List<Member> findByInput(String input);

}
