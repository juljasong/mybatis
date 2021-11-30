package com.example.mybatis.service.impl;

import com.example.mybatis.dao.MemberMapper;
import com.example.mybatis.dao.UrlMapper;
import com.example.mybatis.entity.Member;
import com.example.mybatis.entity.Url;
import com.example.mybatis.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlMapper urlMapper;
    private final MemberMapper memberMapper;

    public int add(Url url) {
        toAbsolutePath(url);
        return urlMapper.insert(url);
    }

    public List<Url> listByMemberName(String name) {
        Long memberId = memberMapper.findMemberIdByMemberName(name);
        List<Url> urls = urlMapper.findByMemberId(memberId);
        return urls;
    }

    public int deleteUrl(Long id) {
        return urlMapper.deleteById(id);
    }

    public int updateUrl(Member loginUser, Url url) {
        toAbsolutePath(url);
        return urlMapper.updateById(loginUser.getId(), url);
    }


    private void toAbsolutePath(Url url) {
        String inputUrl = url.getUrl();

        if (!inputUrl.matches("(http|https)://.*")) {
            log.info("not include");
            url.setUrl("https://" + inputUrl);
        }
    }

    public Url findUrlById(Long memberId, Long id) {
        return urlMapper.findUrlById(memberId, id);
    }

    public List<Url> findByMemberIdAndPublic(String name) {
        Long memberId = memberMapper.findMemberIdByMemberName(name);
        List<Url> urls = urlMapper.findByMemberIdAndPublic(memberId);
        return urls;
    }

    public List<Url> findByMemberId(Long id) {
        return urlMapper.findByMemberId(id);
    }

    public List<Url> findExpiredByMemberId(Long id) {
        return urlMapper.findExpiredByMemberId(id);
    }

    public int countAllByMemberId(Long id) {
        return urlMapper.countAllByMemberId(id);
    }

    public List<Url> findAll() {
        return urlMapper.findAll();
    }

    public List<Url> findByInput(String input) {
        return urlMapper.findByInput(input);
    }
}
