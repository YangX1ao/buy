package com.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shop.core.model.Navigation;
public interface NavigationDao {
	
	@Select("SELECT t.id, t.is_blank_target, t.`name`, t.url FROM xx_navigation t WHERE position=#{position}"
 +" ORDER BY orders")
	List<Navigation> findByPosition(@Param(value="position") Integer position);
}
