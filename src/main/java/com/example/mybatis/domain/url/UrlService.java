package com.example.mybatis.domain.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UrlService {

    private final UrlMapper urlMapper;

    public UrlService(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }

    public int add(Url url) {

        if (url.getExpirationDate().length() == 0) {
            url.setExpirationDate(null);
        }

        String inputUrl = url.getUrl();

        if (!inputUrl.matches("(http|https)://.*")) {
            log.info("not include");
            url.setUrl("https://" + inputUrl);
        }

        return urlMapper.insert(url);
    }

}
