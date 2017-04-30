package com.shop.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shop.core.model.Navigation;
import com.shop.core.util.AssertUtil;
import com.shop.dao.NavigationDao;

@Service
public class NavigationService {
	
	@Resource
	private NavigationDao navigationDao;
	
	/**
	 * 根据位置获取导航菜单数据
	 * @param position 位置
	 * @return
	 */
	public List<Navigation> findByPosition(int position){
		AssertUtil.isTrue(position<0, "请选择一个位置进行查询");
		
		List<Navigation> navigations=navigationDao.findByPosition(position);
		return navigations;
	}
	
	
}
