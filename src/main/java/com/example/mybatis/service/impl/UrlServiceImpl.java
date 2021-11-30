package com.example.mybatis.service.impl;

import com.example.mybatis.dao.MemberDAO;
import com.example.mybatis.dao.UrlDAO;
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

    private final UrlDAO urlDAO;
    private final MemberDAO memberDAO;

    public int add(Url url) {
        toAbsolutePath(url);
        return urlDAO.insert(url);
    }

    public List<Url> listByMemberName(String name) {
        Long memberId = memberDAO.findMemberIdByMemberName(name);
        List<Url> urls = urlDAO.findByMemberId(memberId);
        return urls;
    }

    public int deleteUrl(Long id) {
        return urlDAO.deleteById(id);
    }

    public int updateUrl(Member loginUser, Url url) {
        toAbsolutePath(url);
        return urlDAO.updateById(loginUser.getId(), url);
    }


    private void toAbsolutePath(Url url) {
        String inputUrl = url.getUrl();

        if (!inputUrl.matches("(http|https)://.*")) {
            log.info("not include");
            url.setUrl("https://" + inputUrl);
        }
    }

    public Url findUrlById(Long memberId, Long id) {
        return urlDAO.findUrlById(memberId, id);
    }

    public List<Url> findByMemberIdAndPublic(String name) {
        Long memberId = memberDAO.findMemberIdByMemberName(name);
        List<Url> urls = urlDAO.findByMemberIdAndPublic(memberId);
        return urls;
    }

    public List<Url> findByMemberId(Long id) {
        return urlDAO.findByMemberId(id);
    }

    public List<Url> findExpiredByMemberId(Long id) {
        return urlDAO.findExpiredByMemberId(id);
    }

    public int countAllByMemberId(Long id) {
        return urlDAO.countAllByMemberId(id);
    }

    public List<Url> findAll() {
        return urlDAO.findAll();
    }

    public List<Url> findByInput(String input) {
        return urlDAO.findByInput(input);
    }
}
