package com.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.dto.GoodsDto;
import com.shop.core.model.Goods;

public interface GoodsDao {
		
	@Select("SELECT g.id, g.`name`, g.caption, g.price, g.market_price, g.image FROM xx_goods g LEFT JOIN xx_product_category p on g.product_category=p.id "
			+ "LEFT JOIN xx_goods_tag t on g.id=t.goods where p.tree_path LIKE ',${productCategoryId}%' "
			+ "and t.tags=#{tagId} LIMIT #{count}")
	List<Goods> findGoodsList(@Param(value="productCategoryId") int productCategoryId, 
			@Param(value="tagId")int tagId, @Param(value="count")int count);
	
	
	PageList<Goods> selectForPage(GoodsDto goodsDto,PageBounds pageBounds);
	
	
	
}
