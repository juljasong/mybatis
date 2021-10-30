package com.example.mybatis.domain.login;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;

    public Member login(LoginDTO loginDTO) {

        Member loginUser = memberMapper.findByLoginId(loginDTO.getEmail(), loginDTO.getPassword());

        if (loginUser != null) {
            return loginUser;
        } else {
            return null;
        }

    }
}
