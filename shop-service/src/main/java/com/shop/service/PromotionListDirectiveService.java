package com.shop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.Promotion;
import com.shop.dao.PromotionDao;

@Service
@SuppressWarnings("all")
public class PromotionListDirectiveService {
		
	@Autowired
	private PromotionDao promotionDao;
	
	
	public List<Promotion> findPromotionList(Integer parentId, int  count,boolean hasEnded) {
		return	promotionDao.findPromotionList(parentId,count,hasEnded);
	}

}
