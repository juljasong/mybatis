package com.example.mybatis;

import java.security.MessageDigest;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

    @org.junit.jupiter.api.Test
    public void hash() throws Exception {
        //given
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update("abc@test.com".getBytes());
        int h = "abc@test.com".hashCode();
        //String hashed = md.digest().;
        String merchantUid = date + h;

        //when

        //then
        System.out.println("merchantUid = " + merchantUid);
    }


}
