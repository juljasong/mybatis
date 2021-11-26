package com.example.mybatis.config;

import com.example.mybatis.util.argumentResolver.LoginMemberArgumentResolver;
import com.example.mybatis.util.interceptor.AdminCheckInterceptor;
import com.example.mybatis.util.interceptor.LogInterceptor;
import com.example.mybatis.util.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //ctrl+l
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/images/**", "/*.ico", "/error");
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/admin/**", "/member/add", "/*", "/logout", "/css/**", "/images/**", "/*.ico", "/error", "/auth/login", "/member/chk/*", "/member/findpwd", "/member/resetpwd/*", "/member/resetpwd", "/login/oauth2/*", "/member/oauth2/*");
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(3)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin", "/admin/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
