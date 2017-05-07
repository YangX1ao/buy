package com.shop.service;

import java.util.Collections;
import java.util.List;

import com.shop.core.constant.ProductCategoryGrade;
import com.shop.core.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.ProductCategory;
import com.shop.dao.ProductCategoryDao;

@Service
public class ProductCategoryService {
	
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

	/**
	 * 根据id获取分类
	 * @param productCategoryId
	 * @return
	 */
	public ProductCategory findById(Integer productCategoryId){
		AssertUtil.isTrue(productCategoryId==null ||productCategoryId<1,"请选择一个分类进行查询！");
		ProductCategory productCategory=productCategoryDao.findById(productCategoryId);
		AssertUtil.isTrue(productCategory==null,"该分类不存在！");
		return productCategory;
	}

	/**
	 * 获取父类分级
	 * @param productCategoryId
	 * @return
	 */
	public List<ProductCategory> findParentList(Integer productCategoryId){
		//先查询是否存在
		ProductCategory productCategory = findById(productCategoryId);
		if(productCategory.getGrade()== ProductCategoryGrade.ROOT.getGrade()){
			return Collections.emptyList();
		}
		String treePath=productCategory.getTreePath();
		treePath=treePath.substring(treePath.indexOf(",")+1,treePath.lastIndexOf(","));
		List<ProductCategory> productCategories=productCategoryDao.findParentList(treePath);
		return productCategories;
	}
	
}

