package com.shop.sqlprovider;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PromotionProvider {

	private static Logger logger = LoggerFactory.getLogger(PromotionProvider.class);

	public String findPromotionList(Integer productCategoryId, 
			int count,Boolean hasEnded) {
		SQL sql = new SQL();
		sql.SELECT("id, title, image")
		.FROM("xx_promotion p");
		
		if (productCategoryId != null) {
			sql.LEFT_OUTER_JOIN("xx_product_category_promotion cp on cp.promotions = p.id")
			.WHERE("cp.product_categories = #{productCategoryId}");
		} else {
			sql.WHERE("1=1");
		}
		
		if (hasEnded != null) {
			if (hasEnded) { // 结束
				sql.AND().WHERE("end_date < now()");
			} else { // 没结束
				sql.AND().WHERE("end_date >= now() or end_date is null");
			}
		}
		String sqlStr = sql.toString();
		if (count > 0) {
			sqlStr += " limit #{count}";
		}
		logger.info("findPromotionList sql: {}", sqlStr);
		return sqlStr;
	}
}
