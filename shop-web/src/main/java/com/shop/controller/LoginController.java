package com.shop.controller;

import com.shop.core.base.BaseController;
import com.shop.core.constant.Constant;
import com.shop.core.util.LoginIdentityUtil;
import com.shop.core.vo.LoginIndentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController {
	
	@RequestMapping("login")
	public String login (String redirectUrl, Model model, HttpServletRequest request) {

		//判断用户是否已经登录
		String url=checkIsLogin(request,redirectUrl);
		if(StringUtils.isNotBlank(url)){
			return url;
		}
		model.addAttribute("redirectUrl", redirectUrl);

		return "user/login";
	}

	@RequestMapping("register")
	public String register(String redirectUrl, Model model, HttpServletRequest request) {

		// 用户是否已经登录
		String url = checkIsLogin(request, redirectUrl);
		if (StringUtils.isNotBlank(url)) {
			return url;
		}
		model.addAttribute("redirectUrl", redirectUrl);
		return "user/register";
	}

	/***
	 * 验证用户是否登录
	 * @param request
	 * @param redirectUrl
	 * @return
	 */
	private String checkIsLogin(HttpServletRequest request, String redirectUrl) {
		LoginIndentity loginIndentity = LoginIdentityUtil.getFromSession(request);
		if(loginIndentity==null){
			return null;
		}
		//如果没有详细的链接，则登陆后跳转到首页
		if(StringUtils.isBlank(redirectUrl)){
			String ctx=request.getContextPath();
			return "redirect:"+ctx+"/index";
		}
		//如果有详细的链接，则登录后还跳转到本页面
		return "redirect:"+"/redirectUrl";

	}

}
