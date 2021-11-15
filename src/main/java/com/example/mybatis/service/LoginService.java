package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberMapper;
import com.example.mybatis.dto.LoginDto;
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
