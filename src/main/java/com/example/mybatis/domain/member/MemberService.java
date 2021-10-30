package com.example.mybatis.domain.member;

import com.example.mybatis.domain.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

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

}
