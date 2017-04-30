package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.AdPosition;
import com.shop.dao.AdPositionDao;

@Service
public class AdPositionService {
	
	@Autowired
	private AdPositionDao adPositonDao;

	public AdPosition findById(int id) {
		// 基本参数验证
		AdPosition adPosition = adPositonDao.findById(id);
		return adPosition;
	}

	
}
