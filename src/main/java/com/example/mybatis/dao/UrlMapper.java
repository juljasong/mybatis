package com.example.mybatis.dao;

import com.example.mybatis.entity.Url;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UrlMapper {

    //INSERT INTO urls(member_id, name, url, description, expirationDate, isPublic) VALUES (#{url.memberId}, #{url.name}, #{url.url}, #{url.description}, #{url.expirationDate}, #{url.isPublic});
    @Insert("INSERT INTO urls(member_id, name, url, description, expiration_date, is_public) VALUES (#{url.memberId}, #{url.name}, #{url.url}, #{url.description}, #{url.expirationDate}, #{url.isPublic})")
    int insert(@Param("url") Url url);

    @Select("SELECT * FROM urls WHERE member_id=#{memberId} AND (expiration_date > NOW() OR expiration_date IS NULL)")
    List<Url> findByMemberId(@Param("memberId") Long memberId);

    @Select("SELECT * FROM urls WHERE member_id=#{memberId} AND (expiration_date > NOW() OR expiration_date IS NULL) AND is_public=1")
    List<Url> findByMemberIdAndPublic(@Param("memberId") Long memberId);

    @Select("SELECT * FROM urls WHERE member_id=#{memberId} AND expiration_date < NOW()")
    List<Url> findExpiredByMemberId(@Param("memberId") Long memberId);

    @Delete("DELETE FROM urls WHERE id=#{id}")
    int deleteById(Long id);

    @Update("UPDATE urls SET name=#{url.name}, url=#{url.url}, description=#{url.description}, expiration_date=#{url.expirationDate}, is_public=#{url.isPublic} WHERE id=#{url.id} AND member_id=#{memberId}")
    int updateById(@Param("memberId") Long memberId, @Param("url") Url url);

    @Select("SELECT * FROM urls")
    List<Url> findAll();

    @Select("SELECT * FROM urls WHERE id=#{id} AND member_id=#{memberId}")
    Url findUrlById(@Param("memberId") Long memberId, @Param("id") Long id);

    @Select("SELECT COUNT(*) FROM urls WHERE member_id=#{id}")
    int countAllByMemberId(@Param("id") Long id);
}
