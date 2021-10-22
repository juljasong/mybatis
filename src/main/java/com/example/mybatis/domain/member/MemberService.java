package com.example.mybatis.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberMapper memberMapper;
    
    public Member findMemberById(Long id) {
        return memberMapper.findById(id);
    }

    public void save(Member member, String authKey) {
        member.setAuthKey(authKey);
        System.out.println("authKey = " + authKey);
       memberMapper.insert(member);
    }
}
