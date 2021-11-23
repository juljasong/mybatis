package com.example.mybatis.dao;

import com.example.mybatis.entity.Member;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MemberMapper {

//    @Insert("INSERT INTO members (email, pwd, name, authKey, provider) VALUES(#{member.email}, PASSWORD(#{member.password}), #{member.name}, #{member.authKey})")
    @Insert("<script>" +
            "INSERT INTO members" +
            "(" +
            "email, name, auth_key, pwd" +
            "<if test='member.provider != null'>, provider</if>" +
            ")" +
            "VALUES" +
            "(" +
            "#{member.email}, #{member.name}, #{member.authKey}, PASSWORD(#{member.password})" +
            "<if test='member.provider != null'>, #{member.provider}</if>" +
            ")" +
            "</script>")
    int insert(@Param("member") Member member); // 입력된 경우 1, 실패한 경우 0

    @Select("SELECT id, email, name, provider FROM members WHERE email=#{email} AND pwd=PASSWORD(#{password}) AND auth_key='Y'")
    Member findByLoginId(@Param("email") String email, @Param("password") String password); // 파라미터명 같아도 어노테이션 생략 불가

    @Select("SELECT * FROM members WHERE id=#{id}")
    Member findById(Long id);

    @Select("SELECT id FROM members WHERE name=#{memberName}")
    Long findMemberIdByMemberName(String memberName);

    @Select("SELECT * FROM members")
    List<Member> findAll();

    @Select("UPDATE members SET auth_key='Y' WHERE auth_key=#{authKey}")
    Member mailCheck(@Param("authKey") String authKey);

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

    @Select("SELECT * FROM members WHERE email=#{email}")
    Member findByEmail(@Param("email") String email);

    @Select("SELECT COUNT(*) FROM members WHERE name=#{name}")
    int findByName(@Param("name") String name);

    @Select("SELECT COUNT(*) FROM members WHERE id=#{id} AND pwd=PASSWORD(#{currentPassword})")
    int findByCurrentPassword(@Param("id") Long id, @Param("currentPassword") String currentPassword);

    @Update("UPDATE members SET auth_key=#{authKey} WHERE email=#{email}") // ㅋㅋ 재설정 중에는 로그인할 수 없음
    int setAuthKey(@Param("email") String email, @Param("authKey") String authKey);

    @Update("UPDATE members SET pwd=PASSWORD(#{password}), auth_key='Y' WHERE auth_key=#{authKey}")
    int updatePassword(@Param("password") String password, @Param("authKey") String authKey);

    @Select("SELECT * FROM members WHERE provider=#{provider} AND email=#{email}")
    Member findByEmailAndProvider(@Param("email") String email, @Param("provider") String provider);

    @Update("UPDATE members SET provider=#{provider} WHERE email=#{email}")
    int updateProvider(@Param("email") String email, @Param("provider") String provider);
}
