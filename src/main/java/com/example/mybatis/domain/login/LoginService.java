package com.example.mybatis.domain.login;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.web.login.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;

    public Member login(LoginDto loginDto) {

        Member loginUser = memberMapper.findByLoginId(loginDto.getEmail(), loginDto.getPassword());

        if (loginUser != null) {
            return loginUser;
        } else {
            return null;
        }

    }
}
