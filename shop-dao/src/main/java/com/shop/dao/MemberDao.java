package com.shop.dao;

import com.shop.core.dto.MemberDto;
import com.shop.core.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Repository
public interface MemberDao {

    @Select("SELECT id,username,email,`password`,phone\n" +
            "FROM xx_member WHERE username=#{userName} or email=#{userName}")
    Member findByUserName(@Param(value = "userName") String userName);

    @Select("select id, username, password, phone, email from xx_member where ${column} = #{value}")
    Member findByColumn(@Param(value = "column") String column, @Param(value = "value") String value);


    @Insert("INSERT INTO xx_member (username, password, email, phone, gender, name, mobile, create_date, modify_date, version, "
            + " amount,balance, is_enabled, is_locked, lock_key, login_failure_count, point, register_ip, member_rank) "
            + " VALUES (#{username}, #{password}, #{email}, #{phone}, #{gender}, #{name}, #{mobile}, now(), now(), 0, 0, 0, 1, 0, '', 0, 0, '',1)")
    //使用主键，主键是id
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void register(Member member);

}
