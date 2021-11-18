package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
            String authKey = mailService.sendAuthenticationMail(member);
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

    public String findPassword(String email) {
        try {
            String authKey = UUID.randomUUID().toString().replace("-", "");
            int result = memberMapper.setAuthKey(email, authKey);

            if (result > 0) {
                mailService.sendFindPasswordMail(email, authKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Check your Email";
    }

    public String resetPassword(String password1, String authKey) {
        try {

            int result = memberMapper.updatePassword(password1, authKey);

            if (result > 0) {
                return "Your password has been reset successfully";
            } else {
                return "An error occurred while setting the password.";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String saveOauth2(Member member) {
        int result = memberMapper.insert(member);
        if (result == 0) {
            return "An error has occurred.";
        } else{
            return "Sign in has been completed.";
        }
    }

    public String linkOauth2(String email, String provider) {
        int result = memberMapper.updateProvider(email, provider);
        if (result == 0) {
            return "An error has occurred.";
        } else {
            return "Link has been completed.";
        }
    }




}
