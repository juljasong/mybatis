package com.example.mybatis.service;

import com.example.mybatis.entity.Member;

import java.util.List;

public interface MemberService {

    Member findMemberById(Long id);

    int findCntByName(String name);

    String findPasswordByEmail(String email);

    List<Member> findAllMembers();

    List<Member> findMembersByInput(String input);

    Member findMemberByEmail(String email);

    int verifyCurrentPassword(Long id, String currentPassword);

    int add(Member member) throws Exception;

    String addOauth2(Member member);

    String modifyPassword(String password1, String authKey);

    String modifyProvider(String email, String provider);

    String modifyAuthKey(String authKey);

    int modifyMember(String password, String name, Member loginUser);

}
