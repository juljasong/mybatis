package com.example.mybatis.service;

import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberMapper;
import com.example.mybatis.dto.LoginDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final String GOOGLE_CLIENT_ID = "302683262107-2r7fk6pha8tf4guabk57gk061opgdgsi.apps.googleusercontent.com";
    private final MemberMapper memberMapper;

    public Member login(LoginDto loginDto) {

        Member loginUser = memberMapper.findByLoginId(loginDto.getEmail(), loginDto.getPassword());

        if (loginUser != null) {
            return loginUser;
        } else {
            return null;
        }

    }

    public Member loginOauth2ByGoogle(String credential, String g_csrf_token) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(credential);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            log.info("User ID={}", userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");

            log.info("email={}, emailVerified={}, name={}", email, emailVerified, name);

            Member member = memberMapper.findByEmailAndProvider(email, "google");
            if (member != null) {
                log.info("loginService google1");
                return member;
            } else {
                member = memberMapper.findByEmail(email);
                if (member != null) {
                    log.info("loginService google2");
                    return member;
                } else {
                    return new Member(email);
                }
            }
        } else {
            log.info("Invalid ID token.");
        }
    return null;
    }
}
