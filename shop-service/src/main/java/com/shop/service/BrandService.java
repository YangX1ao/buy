package com.shop.service;

import java.util.Collections;
import java.util.List;

import com.shop.core.constant.ProductCategoryGrade;
import com.shop.core.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.Brand;
import com.shop.dao.BrandDao;

@Service
public class BrandService {
	
	@Autowired
	private BrandDao brandDao;

	@Autowired
	private ProductCategoryService productCategoryService;
	
	public List<Brand> findBrands (Integer productCategoryId,int count){
		if(count<1){
			return Collections.emptyList();
		}
		List<Brand> brands=null;
		if(productCategoryId!=null && productCategoryId>0){
			//获取分类
			ProductCategory productCategory=productCategoryService.findById(productCategoryId);
			if(productCategory.getGrade()!= ProductCategoryGrade.ROOT.getGrade()){
				String treePath=productCategory.getTreePath();
				String [] treePaths=treePath.split(",");
				String productCategoryIdStr=treePaths[1];
				productCategoryId=Integer.parseInt(productCategoryIdStr);
			}
			brands=brandDao.findBrandList(productCategoryId, count);
		}else{
			brands=brandDao.findBrandList(1, count);
		}
		return brands;
	}
	
	
}
