package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.core.base.BaseController;
import com.shop.core.base.ResultListInfo;
import com.shop.core.dto.GoodsDto;
import com.shop.core.model.Goods;
import com.shop.service.GoodsService;

@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController {
	
	@Autowired
	private GoodsService goodsService;
	@RequestMapping("search")
	public String search(Model model,GoodsDto goodsDto) {
		//ResultListInfo<Goods> result = goodsService.selectForPage(goodsDto);
		//model.addAttribute("resultList", result);
		return "goods/search";
	}
	
}
