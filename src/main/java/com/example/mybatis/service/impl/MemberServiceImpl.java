package com.example.mybatis.service.impl;

import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberDAO;
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
    
    private final MemberDAO memberDAO;
    private final MailService mailService;

    public Member findMemberById(Long id) {
        return memberDAO.selectMemberById(id);
    }

    public int add(Member member) throws Exception {
        int result = 0;
        try {
            String authKey = mailService.sendAuthenticationMail(member);
            member.setAuthKey(authKey);
            System.out.println("authKey = " + authKey);
            result = memberDAO.insert(member);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int verifyCurrentPassword(Long id, String currentPassword) {
        return memberDAO.selectCntByIdAndPassword(id, currentPassword);
    }

    public String findPasswordByEmail(String email) {
        try {
            String authKey = UUID.randomUUID().toString().replace("-", "");
            int result = memberDAO.updateAuthKeyByEmail(email, authKey);

            if (result > 0) {
                mailService.sendFindPasswordMail(email, authKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Check your Email";
    }

    public String modifyPassword(String password1, String authKey) {
        try {

            int result = memberDAO.updatePassword(password1, authKey);

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

    public String addOauth2(Member member) {
        int result = memberDAO.insert(member);
        if (result == 0) {
            return "An error has occurred.";
        } else{
            return "Sign in has been completed.";
        }
    }

    public String modifyProvider(String email, String provider) {
        int result = memberDAO.updateProvider(email, provider);
        if (result == 0) {
            return "An error has occurred.";
        } else {
            return "Link has been completed.";
        }
    }

    public String modifyAuthKey(String authKey) {
        int result = memberDAO.updateAuthKey(authKey);
        String msg = "";
        if (result < 1) {
            msg = "An error has occurred.";
        }else {
            msg = "Membership registration has been completed.";
        }
        return msg;
    }

    public List<Member> findAllMembers() {
        return memberDAO.selectAllMember();
    }

    public List<Member> findMembersByInput(String input) {
        return memberDAO.selectMembersByInput(input);
    }

    public Member findMemberByEmail(String email) {
        return memberDAO.selectMemberByEmail(email);
    }

    public int findCntByName(String name) {
        return memberDAO.selectCntByName(name);
    }

    public int modifyMember(String password, String name, Member loginUser) {
        return memberDAO.update(password, name, loginUser);
    }

}
