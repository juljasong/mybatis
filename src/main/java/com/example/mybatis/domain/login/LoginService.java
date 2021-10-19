package com.example.mybatis.domain.login;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;

    public Member login(String email, String password) {

        Member loginUser = memberMapper.findByLoginId(email, password);

        if (loginUser != null) {
            return loginUser;
        } else {
            return null;
        }

    }
}
