package com.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shop.core.model.Article;

public interface ArticleDao {
	
	@Select("SELECT id, title FROM xx_article t where "
			+ "article_category = #{articleCategoryId} ORDER BY t.hits desc limit #{count}")
	List<Article> findArticleList(@Param(value="articleCategoryId")Integer articleCategoryId, 
			@Param(value="count")int count);

}
