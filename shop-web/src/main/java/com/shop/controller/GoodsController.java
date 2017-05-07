package com.shop.controller;

import com.shop.core.constant.GoodsSortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.core.base.BaseController;
import com.shop.core.base.ResultListInfo;
import com.shop.core.dto.GoodsDto;
import com.shop.core.model.Goods;
import com.shop.service.GoodsService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController {
	
	@Autowired
	private GoodsService goodsService;
	@RequestMapping("search")
	public String search(Model model,GoodsDto goodsDto) {
		ResultListInfo<Goods> result = goodsService.search(goodsDto);
		model.addAttribute("resultList", result);

		GoodsSortType[] goodsSortTypes = GoodsSortType.values();
		model.addAttribute("orderTypes", goodsSortTypes);

		model.addAttribute("currentOrderType", GoodsSortType.findByType(goodsDto.getSort()));
		return "goods/search";
	}

	@RequestMapping("list/{productCategoryId}")
	public String search(Model model, @PathVariable Integer productCategoryId,GoodsDto goodsDto){
		Object[] result=goodsService.findProductCategoryGoods(productCategoryId,goodsDto);
		ResultListInfo <Goods> resultList=(ResultListInfo<Goods>) result[1];
		model.addAttribute("resultList",resultList);
		model.addAttribute("productCategory",result[0]);

		GoodsSortType[] goodsSortTypes = GoodsSortType.values();
		model.addAttribute("orderTypes", goodsSortTypes);

		model.addAttribute("currentOrderType", GoodsSortType.findByType(goodsDto.getSort()));

		return "goods/list";
	}

	@RequestMapping("content/{id}")
	public String findById(Model model,@PathVariable Integer id){
		Map<String,Object> result=goodsService.findById(id);
		model.addAllAttributes(result);
		return "goods/detail";
	}

	@RequestMapping("content/{id}.json")
	@ResponseBody
	public Map<String, Object> findByIdJson(Model model, @PathVariable Integer id) {

		Map<String, Object> result = goodsService.findById(id);
		return result;
	}

}
