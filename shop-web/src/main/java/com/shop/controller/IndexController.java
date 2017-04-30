package com.shop.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.core.base.BaseController;

@Controller
@SuppressWarnings("all")
public class IndexController extends BaseController{
	
	@RequestMapping("index")
	public String index (Model model){
		return "index";
	}
	
	
	
}
