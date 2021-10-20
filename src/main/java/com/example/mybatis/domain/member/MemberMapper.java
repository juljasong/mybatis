package com.example.mybatis.domain.member;

import com.example.mybatis.web.SessionConst;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    @Update({"<script>",
            "UPDATE members",
            "  <set>",
            "    <if test='password != null'>pwd=PASSWORD(#{password}),</if>",
            "    <if test='name != null'>name=#{name}</if>",
            "  </set>",
            "WHERE id=#{loginUser.id}",
            "</script>"})
    int Update(@Param("password") String password,
               @Param("name") String name,
               @Param("loginUser") Member loginUser);

}
