package com.example.mybatis.domain.url;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UrlMapper {

    //INSERT INTO urls(member_id, name, url, description, expirationDate, isPublic) VALUES (#{url.memberId}, #{url.name}, #{url.url}, #{url.description}, #{url.expirationDate}, #{url.isPublic});
    @Insert("INSERT INTO urls(member_id, name, url, description, expirationDate, isPublic) VALUES (#{url.memberId}, #{url.name}, #{url.url}, #{url.description}, #{url.expirationDate}, #{url.isPublic})")
    int insert(@Param("url") Url url);

    @Select("SELECT * FROM urls WHERE member_id=#{memberId} AND (expirationDate > NOW() OR expirationDate IS NULL)")
    List<Url> findByMemberId(@Param("memberId") Long memberId);

    @Select("SELECT * FROM urls WHERE member_id=#{memberId} AND expirationDate < NOW()")
    List<Url> findExpiredByMemberId(@Param("memberId") Long memberId);

}
