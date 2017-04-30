package com.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shop.core.model.Brand;

public interface BrandDao {
	
	
	@Select("SELECT id, name, logo FROM xx_brand b LEFT JOIN xx_product_category_brand cb  "
			+ "on cb.brands=b.id WHERE cb.product_categories=#{productCategoryId} "
			+ "ORDER BY orders LIMIT #{count}")
	List<Brand> findBrandList(@Param(value="productCategoryId")Integer productCategoryId,
	@Param(value="count") int count);
		
	@Select("SELECT id, name, logo FROM xx_brand b WHERE type=#{type} "
			+ "ORDER BY orders LIMIT #{count}")
	List<Brand> findBrandListByType(@Param(value="type") int type,@Param(value="count") int count);
}
	


	
	
	

