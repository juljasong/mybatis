package com.example.mybatis.domain;

import com.example.mybatis.domain.member.Member;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class MailService {

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "1d2f.portfoli@gmail.com";

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String authenticationMailSend(Member member) {

        String authKey = UUID.randomUUID().toString().replace("-", "");
        String htmlStr = member.getName() + "님, MyBatis에서 회원 가입이 이루어졌습니다.<br><br>" +
                "<a href='http://localhost:8080/member/"+ authKey + "'>해당 링크</a>를 클릭하시면 회원 가입이 완료됩니다.<br>" ;

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject("[본인인증] MyBatis: " + member.getName() + "님의 인증메일입니다");
            messageHelper.setTo("zpion@naver.com");
            //messageHelper.setTo(member.getEmail());
            messageHelper.setFrom(FROM_ADDRESS, "MyBatis_auto");
            messageHelper.setText(htmlStr, true);
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authKey;
    }

    public void sendPreConfiguredMail(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
