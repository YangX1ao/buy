package com.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shop.core.model.ProductCategory;

public interface ProductCategoryDao {
	
	@Select("SELECT id, name FROM xx_product_category t WHERE t.parent is NULL order by orders limit #{count}")
	List<ProductCategory> findRootCategories(@Param(value="count")Integer count);
	
	@Select("SELECT id, name FROM xx_product_category t WHERE t.parent = #{parentId} order by orders limit #{count}")
	List<ProductCategory> findChildrenCategories(@Param(value="parentId")Integer parentId, 
			@Param(value="count")int count);
	
}
