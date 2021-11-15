package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
    
    private final MemberMapper memberMapper;
    private final MailService mailService;

    public MemberService(MemberMapper memberMapper, MailService mailService) {
        this.memberMapper = memberMapper;
        this.mailService = mailService;
    }

    public Member findMemberById(Long id) {
        return memberMapper.findById(id);
    }

    public void save(Member member) throws Exception {
        try {
            String authKey = mailService.authenticationMailSend(member);
            member.setAuthKey(authKey);
            System.out.println("authKey = " + authKey);
            memberMapper.insert(member);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int verifyCurrentPassword(Long id, String currentPassword) {
        return memberMapper.findByCurrentPassword(id, currentPassword);
    }
}
