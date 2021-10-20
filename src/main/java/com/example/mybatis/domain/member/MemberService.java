package com.example.mybatis.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberMapper memberMapper;
    
    public Member findMemberById(Long id) {
        return memberMapper.findById(id);
    }
}
