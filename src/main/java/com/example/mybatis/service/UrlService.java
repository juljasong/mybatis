package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Url;

import java.util.List;

public interface UrlService {

    int addUrl(Url url);

    int removeUrl(Long id);

    int modifyUrl(Member loginUser, Url url);

    int findCntByMemberId(Long id);

    Url findUrlById(Long id);

    List<Url> findUrlsByMemberName(String name);

    List<Url> findUrlsByMemberIdAndPublic(String name);

    List<Url> findEnabledUrlsByMemberId(Long id);

    List<Url> findExpiredUrlsByMemberId(Long id);

    List<Url> findAllUrls();

    List<Url> findUrlsByInput(String input);

}
