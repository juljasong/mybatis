package com.example.mybatis.mapper;

import com.example.mybatis.domain.Member;
import com.example.mybatis.domain.MemberDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO members (email, pwd, name) VALUES(#{member.email}, PASSWORD(#{member.password}), #{member.name})")
    int insert(@Param("member") Member member); // 입력된 경우 1, 실패한 경우 0


    @Select("SELECT * FROM members WHERE email=#{member.email} AND pwd=PASSWORD(#{member.password})")
    Member login(@Param("member") MemberDTO member);


}
