package com.shop.service;

import com.shop.core.dto.MemberDto;
import com.shop.core.model.Member;
import com.shop.core.util.AssertUtil;
import com.shop.core.util.MD5;
import com.shop.core.vo.LoginIndentity;
import com.shop.dao.MemberDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Service
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    /***
     * 登录
     * @param userName
     * @param password
     * @param verifyCode
     * @param sessionVerifyCode
     * @return
     */
    public LoginIndentity login(String userName, String password, String verifyCode,
                                String sessionVerifyCode) {
        //基本参数非空验证
        AssertUtil.notNull(userName, "请输入用户名或邮箱！");
        AssertUtil.notNull(password, "请输入密码！");
        AssertUtil.notNull(verifyCode, "请输入验证码！");
        AssertUtil.isTrue(!verifyCode.equals(sessionVerifyCode), "验证码不正确，请重新输入！");
        //基本参数查询
        Member member = memberDao.findByUserName(userName);
        AssertUtil.isTrue(member == null, "该用户不存在！");

        password = MD5.toMD5(password);
        AssertUtil.isTrue(!password.equals(member.getPassword()), "用户名或者密码不正确！");
        //构建登录信息
        // 构建一个登录信息
        LoginIndentity loginIndentity = buildLoginIndentity(member);
        return loginIndentity;
    }


    /***
     * 注册
     * @param memberDto
     * @param verifyCode
     * @param phoneVerifyCode
     * @return
     */
    public LoginIndentity  register(MemberDto memberDto,
                                   String verifyCode, String phoneVerifyCode) {

        //基本参数验证
        checkRegisterInfomation(memberDto,verifyCode,phoneVerifyCode);
        //唯一性校验
        checkRegisterOnlyOne(memberDto);

        //保存数据
        String password = memberDto.getPassword();
        Member member=new Member();
        memberDto.setPassword(MD5.toMD5(password));
        BeanUtils.copyProperties(memberDto,member);
        memberDao.register(member);
        //构建一个登录信息结果集
        LoginIndentity loginIndentity=buildLoginIndentity(member);
        return  loginIndentity;
    }

    /***
     * 构建一个登录信息结果集
     * @param member
     * @return
     */
    private LoginIndentity buildLoginIndentity(Member member) {
        LoginIndentity loginIndentity=new LoginIndentity();
        BeanUtils.copyProperties(member,loginIndentity);
        return loginIndentity;
    }

    /***
     * 基本参数验证
     * @param memberDto
     * @param verifyCode
     * @param phoneVerifyCode
     */
    private static void checkRegisterInfomation(MemberDto memberDto,
                                                String verifyCode, String phoneVerifyCode){
        AssertUtil.notNull(memberDto.getUsername() , "请输入用户名！");
        AssertUtil.notNull(memberDto.getPassword() , "请输入密码！");
        AssertUtil.notNull(memberDto.getEmail(), "请输入邮箱！");
        AssertUtil.notNull(memberDto.getPhone() , "请输入手机号！");
        AssertUtil.notNull(memberDto.getVerifyCode(), "请输入验证码！");
        AssertUtil.notNull(memberDto.getPhoneVerifyCode(), "请输入手机验证码！");

        AssertUtil.isTrue(!memberDto.getPassword().equals(memberDto.getRePassword()), "您两次输入的密码不一致，请确认！");
        AssertUtil.isTrue(!memberDto.getPhoneVerifyCode().toLowerCase().equals(phoneVerifyCode), "手机验证码有误，请重新输入！");
        AssertUtil.isTrue(!memberDto.getVerifyCode().toLowerCase().equals(verifyCode),"验证码不正确，请重新输入！");

    }

    /***
     * 唯一性校验
     * @param memberDto
     */
    private void checkRegisterOnlyOne(MemberDto memberDto){
        Member username = memberDao.findByColumn("username", memberDto.getUsername());
        AssertUtil.isTrue(username!=null,"该用户名已注册!");
        Member email = memberDao.findByColumn("email", memberDto.getEmail());
        AssertUtil.isTrue(email!=null,"该邮箱已注册！");
        Member phone = memberDao.findByColumn("phone", memberDto.getPhone());
        AssertUtil.isTrue(phone!=null,"该手机号已注册！");

    }
}
