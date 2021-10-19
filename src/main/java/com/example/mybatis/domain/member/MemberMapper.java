package com.example.mybatis.domain.member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO members (email, pwd, name) VALUES(#{member.email}, PASSWORD(#{member.password}), #{member.name})")
    int insert(@Param("member") Member member); // 입력된 경우 1, 실패한 경우 0

    @Select("SELECT * FROM members WHERE email=#{email} AND pwd=PASSWORD(#{password})")
    Member findByLoginId(@Param("email") String email, @Param("password") String password); // 파라미터명 같아도 어노테이션 생략 불가

    @Select("SELECT * FROM members WHERE id=#{id}")
    Member findById(Long id);

    @Select("SELECT * FROM members")
    List<Member> findAll();

}
