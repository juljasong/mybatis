package com.example.mybatis.domain.url;

import com.example.mybatis.domain.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlService {

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

    public int updateUrl(Long memberId, Url url) {
        toAbsolutePath(url);
        return urlMapper.updateById(memberId, url);
    }


    private void toAbsolutePath(Url url) {
        String inputUrl = url.getUrl();

        if (!inputUrl.matches("(http|https)://.*")) {
            log.info("not include");
            url.setUrl("https://" + inputUrl);
        }
    }
}
