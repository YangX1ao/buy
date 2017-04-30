package com.shop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.ArticleCategory;
import com.shop.dao.ArticleCategoryDao;

@Service
@SuppressWarnings("all")
public class AriticleCategoryService {
	
	
	@Autowired
	private ArticleCategoryDao articleCategoryDao;

	public List<ArticleCategory> findRootList(int count) {
		List<ArticleCategory> rootList = articleCategoryDao.findRootList(count);
		return rootList;
	}
	
	
}
