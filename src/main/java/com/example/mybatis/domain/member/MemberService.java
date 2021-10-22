package com.example.mybatis.domain.member;

import com.example.mybatis.domain.MailService;
import org.springframework.stereotype.Service;


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

        int result = memberMapper.findByEmail(member.getEmail());

        if (result < 1) {
            String authKey = mailService.authenticationMailSend(member);
            member.setAuthKey(authKey);
            System.out.println("authKey = " + authKey);
            memberMapper.insert(member);
        } else {
            throw new Exception("Exist same email");
        }

    }

}
