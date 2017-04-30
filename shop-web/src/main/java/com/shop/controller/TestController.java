package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@SuppressWarnings("all")
public class TestController {

	/*@RequestMapping(value="index",method=RequestMethod.GET)
	@ResponseBody
	public String index(){
		return "hello world";
	}*/
	
	@GetMapping("{userName}/{userPwd}")
	public String test(@PathVariable String userName,@PathVariable Integer userPwd){
		return userName+" : "+userPwd;
	}
	
	
}
