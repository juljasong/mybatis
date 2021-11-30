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

    public int addUrl(Url url) {
        toAbsolutePath(url);
        return urlDAO.insert(url);
    }

    public List<Url> findUrlsByMemberName(String name) {
        Long memberId = memberDAO.selectMemberIdByMemberName(name);
        List<Url> urls = urlDAO.selectByEnabledUrlsMemberId(memberId);
        return urls;
    }

    public int removeUrl(Long id) {
        return urlDAO.deleteUrlById(id);
    }

    public int modifyUrl(Member loginUser, Url url) {
        toAbsolutePath(url);
        return urlDAO.updateUrlById(loginUser.getId(), url);
    }


    private void toAbsolutePath(Url url) {
        String inputUrl = url.getUrl();

        if (!inputUrl.matches("(http|https)://.*")) {
            log.info("not include");
            url.setUrl("https://" + inputUrl);
        }
    }

    public Url findUrlById(Long memberId, Long id) {
        return urlDAO.selectUrlById(memberId, id);
    }

    public List<Url> findUrlsByMemberIdAndPublic(String name) {
        Long memberId = memberDAO.selectMemberIdByMemberName(name);
        List<Url> urls = urlDAO.selectEnabledUrlsByMemberIdAndPublic(memberId);
        return urls;
    }

    public List<Url> findEnabledUrlsByMemberId(Long id) {
        return urlDAO.selectByEnabledUrlsMemberId(id);
    }

    public List<Url> findExpiredUrlsByMemberId(Long id) {
        return urlDAO.selectExpiredUrlsByMemberId(id);
    }

    public int findCntByMemberId(Long id) {
        return urlDAO.selectCntByMemberId(id);
    }

    public List<Url> findAllUrls() {
        return urlDAO.selectAllUrls();
    }

    public List<Url> findUrlsByInput(String input) {
        return urlDAO.selectUrlsByInput(input);
    }
}
