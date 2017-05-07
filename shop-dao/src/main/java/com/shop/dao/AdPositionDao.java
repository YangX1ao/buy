package com.shop.dao;

import com.shop.core.model.AdPosition;
import org.springframework.stereotype.Repository;

@Repository
public interface AdPositionDao {
	
	AdPosition findById(int id);
}
