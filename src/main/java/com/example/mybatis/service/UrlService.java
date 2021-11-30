package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Url;

import java.util.List;

public interface UrlService {

    int add(Url url);

    int deleteUrl(Long id);

    int updateUrl(Member loginUser, Url url);

    int countAllByMemberId(Long id);

    Url findUrlById(Long memberId, Long id);

    List<Url> listByMemberName(String name);

    List<Url> findByMemberIdAndPublic(String name);

    List<Url> findByMemberId(Long id);

    List<Url> findExpiredByMemberId(Long id);

    List<Url> findAll();

    List<Url> findByInput(String input);

}
