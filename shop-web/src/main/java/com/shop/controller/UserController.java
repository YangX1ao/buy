package com.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Constants;
import com.shop.core.base.ResultInfo;
import com.shop.core.constant.Constant;
import com.shop.core.dto.MemberDto;
import com.shop.core.vo.LoginIndentity;
import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.base.BaseController;
import com.shop.core.base.ResultListInfo;
import com.shop.core.dto.UserDto;
import com.shop.core.model.User;
import com.shop.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends BaseController{

	@Resource
	private UserService userService;

	@Autowired
	private MemberService memberService;
	
	
	/*@RequestMapping("addUser")
	@ResponseBody
	public Map<String, Object> addUser(User user){
		Map<String, Object> map=new HashMap<>();
		map.put("resultCode", 200);
		map.put("resultMsg", "操作成功！");
		Integer userId=userService.addUser(user);
		map.put("userId", userId);
		return map;
	}
	
	@RequestMapping(value="updateUser",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(User user){
		Integer line = userService.update(user);
		Map<String, Object> map=new HashMap<>();
		map.put("resultCode", 200);
		map.put("resultMsg", "操作成功！");
		map.put("line", line);
		return map;
	}
	
	//@RequestMapping(value="queryUserById",method=RequestMethod.GET)
	@GetMapping(value="queryUserById")
	public Map<String, Object> queryUserById(Integer id){
		User user = userService.queryUserById(id);
		Map<String, Object> map=new HashMap<>();
		map.put("resultCode", 200);
		map.put("resultMsg", "操作成功！");
		map.put("user", user);
		return map;
	}
	
	@RequestMapping(value="queryUserByUname",method=RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> queryUserByUname(String uname){
		List<User> users = userService.queryUserByUname(uname);
		Map<String, Object> map=new HashMap<>();
		map.put("resultCode", 200);
		map.put("resultMsg", "操作成功！");
		map.put("users", users);
		return map;
		
	}
	
	//@RequestMapping(value="deleteUserById",method=RequestMethod.GET)
	@ResponseBody
	@PutMapping(value="deleteUserById")
	public Map<String , Object> deleteUserById(Integer id){
		int i = userService.delete(id);
		Map<String, Object> map=new HashMap<>();
		map.put("resultCode", 200);
		map.put("ResultMsg", "操作成功！");
		map.put("i", i);
		return map;
	}*/
	
	/*@GetMapping("list")
	public String selectForPage(UserDto userDto) {
		PageList<User> users = userService.selectForPage(userDto);
		ResultListInfo<User> result = buildSuccessResultList(users, userDto);
		return "list";
	}
	
	@GetMapping("list.json")
	@ResponseBody
	public ResultListInfo<User> selectForPageJSON(UserDto userDto) {
		PageList<User> users = userService.selectForPage(userDto);
		ResultListInfo<User> result = buildSuccessResultList(users, userDto);
		return result;
	}*/
	@RequestMapping("login")
	@ResponseBody
	public ResultInfo login(String userName, String password,
									 String verifyCode, HttpServletRequest request){

		String sessionVerifyCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		LoginIndentity loginIndentity = memberService.login(userName, password, verifyCode, sessionVerifyCode);

		request.getSession().setAttribute(Constant.USER_SESSION_KEY, loginIndentity);
		return success("登录成功！");
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute(Constant.USER_SESSION_KEY);
		String ctx=request.getContextPath();
		return  "redirect:"+ctx+"/index";

	}

	@RequestMapping("register")
	@ResponseBody
	public ResultInfo register(MemberDto memberDto,HttpServletRequest request){
		HttpSession session = request.getSession();
		String verifyCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		String phoneVerifyCode = (String) session.getAttribute(Constant.VERIFYCODE_SESSION_KEY);
		LoginIndentity loginIndentity = memberService.register(memberDto, verifyCode, phoneVerifyCode);
		request.getSession().setAttribute(Constant.USER_SESSION_KEY,loginIndentity);
		return success("注册成功！");
	}
}
