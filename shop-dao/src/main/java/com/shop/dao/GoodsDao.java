package com.shop.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.dto.GoodsDto;
import com.shop.core.model.Goods;
import com.shop.core.model.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsDao {
		
	@Select("SELECT g.id, g.`name`, g.caption, g.price, g.market_price, g.image FROM xx_goods g LEFT JOIN xx_product_category p on g.product_category=p.id "
			+ "LEFT JOIN xx_goods_tag t on g.id=t.goods where p.tree_path LIKE ',${productCategoryId}%' "
			+ "and t.tags=#{tagId} LIMIT #{count}")
	List<Goods> findGoodsList(@Param(value="productCategoryId") int productCategoryId, 
			@Param(value="tagId")int tagId, @Param(value="count")int count);


	/**
	 * 热销商品展示
	 * @param tagId
	 * @param count
	 * @return
	 */
	@Select("SELECT g.id,g.`name`,g.image,g.caption,g.market_price,g.price " +
			"FROM xx_goods g " +
			"LEFT JOIN xx_goods_tag t on g.id=t.goods WHERE t.tags=#{tagId}  limit #{count}")
	List<Goods> findHotGoodsList(@Param(value="tagId") int tagId,@Param(value="count") int count );

	/***
	 * 分页查询
	 * @param goodsDto
	 * @param pageBounds
	 * @return
	 */
	PageList<Goods> search(GoodsDto goodsDto,PageBounds pageBounds);


	PageList<Goods> list(GoodsDto goodsDto,PageBounds pageBounds);

	@Select("select g.id, g.`name`, g.caption, g.price, g.market_price, g.image, sn, "
			+ "parameter_values,specification_items, product_images, introduction,product_category "
			+ "from xx_goods g where id = #{id}")
	Goods findById(Integer id);
}
