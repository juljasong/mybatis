package com.example.mybatis.service.impl;

import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberMapper;
import com.example.mybatis.service.MailService;
import com.example.mybatis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberMapper memberMapper;
    private final MailService mailService;

    public Member findMemberById(Long id) {
        return memberMapper.findById(id);
    }

    public int save(Member member) throws Exception {
        int result = 0;
        try {
            String authKey = mailService.sendAuthenticationMail(member);
            member.setAuthKey(authKey);
            System.out.println("authKey = " + authKey);
            result = memberMapper.insert(member);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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


    public String mailCheck(String authKey) {
        int result = memberMapper.mailCheck(authKey);
        String msg = "";
        if (result < 1) {
            msg = "An error has occurred.";
        }else {
            msg = "Membership registration has been completed.";
        }
        return msg;
    }

    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    public List<Member> findByInput(String input) {
        return memberMapper.findByInput(input);
    }

    public Member findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    public int findByName(String name) {
        return memberMapper.findByName(name);
    }

    public int update(String password, String name, Member loginUser) {
        return memberMapper.update(password, name, loginUser);
    }

}
