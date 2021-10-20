package com.example.mybatis.domain.url;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlMapper urlMapper;

    public UrlService(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }

    public int add(Url url) {

        if (url.getExpirationDate().length() == 0) {
            url.setExpirationDate(null);
        }
        return urlMapper.insert(url);
    }

}
