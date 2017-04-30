package com.shop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.base.ResultListInfo;
import com.shop.core.dto.GoodsDto;
import com.shop.core.model.Goods;
import com.shop.core.util.ResultListInfoUtil;
import com.shop.dao.GoodsDao;

@Service
@SuppressWarnings("all")
public class GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;

	public List<Goods> findGoodsList(int productCategoryId, int tagId,int count) {
		List<Goods> goodsList = goodsDao.findGoodsList(productCategoryId, tagId, count);
		return goodsList;
	}
	
	/***
	 * 搜索
	 * @param goodsDto
	 * @return
	 */
	public ResultListInfo<Goods> selectForPage(GoodsDto goodsDto){
		
		PageList<Goods> result=  goodsDao.selectForPage(goodsDto, goodsDto.toPageBounds());
		ResultListInfoUtil<Goods> resultListInfoUtil=new ResultListInfoUtil<>();
		ResultListInfo<Goods> resultListInfo=resultListInfoUtil.buildSuccessResultList(result, goodsDto);
		return resultListInfo;
	}
	
	
	
}
