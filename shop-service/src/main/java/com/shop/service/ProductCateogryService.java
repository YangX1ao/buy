package com.shop.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.ProductCategory;
import com.shop.dao.ProductCategoryDao;

@Service
public class ProductCateogryService {
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	/**
	 * 获取第一级分类
	 * @param count
	 * @return
	 */
	public List<ProductCategory> findRootCategories(int count){
		if(count==0){
			count=6;
		}
		List<ProductCategory> productCategories = productCategoryDao.findRootCategories(count);
		return productCategories;
	}
	
	/**
	 * 获取子级菜单
	 * @param parentId
	 * @param count
	 * @return
	 */
	public List<ProductCategory> findChildrenList(Integer parentId,int count){
		if(parentId==null){
			return Collections.emptyList();
		}
		List<ProductCategory> productCategories = productCategoryDao.findChildrenCategories(parentId, count);
		return productCategories;
		
	}
	
}
