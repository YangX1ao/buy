package com.shop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.Article;
import com.shop.dao.ArticleDao;

@Service
@SuppressWarnings("all")
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;

	public List<Article> findArticleList(Integer articleCategoryId, int count) {
		// TODO Auto-generated method stub
		
		List<Article> articleList = articleDao.findArticleList(articleCategoryId, count);
		return articleList;
	}
	
}
